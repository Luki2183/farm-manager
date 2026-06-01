/** The Google Maps instance. */
let map;
/** The TerraDraw instance managing polygon drawing and selection. */
let draw;
/** The TerraDraw feature ID of the currently selected polygon, or null if none selected. */
let selectedFeatureId = null;
/** A deep clone of the selected feature's geometry at the time of selection, used to detect changes on deselect. */
let geometryCopyOfSelected = null
/** Maps feature IDs to their ordered geometry history snapshots, used for undo. */
const historyToUndo = new Map()
/** Maps feature IDs to their last loaded field info payload, used to restore info on undo. */
const fieldInfoHistory = new Map()
/** Ordered sequence of feature IDs that have been modified, used to determine undo order. */
const historySequence = []

/** The saved map center coordinates loaded from settings. */
let savedMapCenter = null;

/** Maps grain type names to their hex color strings, loaded from settings. */
let colorMap = new Map;

/** Maps feature IDs to their grain type, used for color lookup during rendering. */
const grainMap = new Map;

/**
 * Initializes the Google Maps instance and TerraDraw, then loads all
 * persisted polygons and application settings.
 *
 * Waits for the map projection to be ready before setting up TerraDraw
 * modes, event listeners, and initial data load. Also wires up draw
 * event handlers for select, deselect, and finish.
 *
 * @async
 */
async function initMap() {
    console.info(`Initializing map`)
    map = new google.maps.Map(document.getElementById("map"), {
        center: savedMapCenter,
        zoom: 15,
        streetViewControl: false
    });
    google.maps.event.addListenerOnce(map, "projection_changed", () =>{
        console.debug(`Map projection ready, initializing TerraDraw`)
        draw = new terraDraw.TerraDraw({
            adapter: new terraDrawGoogleMapsAdapter.TerraDrawGoogleMapsAdapter({
                lib: google.maps,
                map: map
            }),
            modes: [
                new terraDraw.TerraDrawPolygonMode({
                    styles: {
                        closingPointColor: ({ id }) => {
                            return pickColor(id);
                        },
                        fillColor: ({ id }) => {
                            return pickColor(id);
                        },
                        outlineColor: "#343434"
                    },
                    snapping: {
                        toLine: false,
                        toCoordinate: true,
                    }
                }),
                new terraDraw.TerraDrawSelectMode({
                    styles: {
                        selectionPointColor: "#44FF00",
                        midPointColor: "#44FF00",
                        selectedPolygonColor: "#44FF00",
                        selectedPolygonOutlineColor: "#1e1e1e",
                        selectedPolygonFillOpacity: 0.2,
                    },
                    flags: {
                        polygon: {
                            feature: {
                                // The entire Feature can be moved
                                draggable: true,

                                // Individual coordinates that make up the Feature...
                                coordinates: {
                                    // Midpoint be added
                                    midpoints: {
                                        // Midpoint be dragged
                                        draggable: true
                                    },

                                    // Can be moved
                                    draggable: true,

                                    // Can snap to other coordinates from geometries _of the same mode_
                                    snappable: true,

                                    // Can be deleted
                                    deletable: true,
                                },
                            },
                        },
                    },
                })
            ]
        });

        draw.start();
        console.debug(`TerraDraw started`)
        prepareButtonsVisual()
        setMode("select")

        draw.on("select", (featureId) => {
            console.info(`Selected feature with id=%o`, featureId)
            geometryCopyOfSelected = structuredClone(draw.getSnapshotFeature(featureId).geometry)
            selectedFeatureId = featureId;
            loadFieldInfo(featureId)
        })
        draw.on("deselect", () => {
            console.info(`Deselected feature with id=%o`, selectedFeatureId)
            let change = JSON.stringify(geometryCopyOfSelected) !== JSON.stringify(draw.getSnapshotFeature(selectedFeatureId).geometry)
            if (change) {
                console.debug(`Geometry changed on deselect, trying to save/update for id=%o`, selectedFeatureId)
                addOrUpdateOnFinishOrDeselect(selectedFeatureId)
            }
            selectedFeatureId = null;
            geometryCopyOfSelected = null
            clearFieldPanel()
        })
        draw.on("finish", (featureId) => {
            console.info(`Finished drawing feature with id=%o`, featureId)
            addOrUpdateOnFinishOrDeselect(featureId)
        })

        loadPolygons()
        clearFieldPanel()
        loadSettings()
    })
}

/**
 * Fetches field info for the given field ID from the API and populates
 * the field info panel. Also caches the result in {@link fieldInfoHistory}
 * and refreshes the polygon color.
 *
 * @param {string} fieldId - The business identifier of the field.
 */
function loadFieldInfo(fieldId) {
    console.debug(`Loading fieldInfo for id=%o`, fieldId)
    fetch(`api/fieldInfo/${fieldId}`)
        .then(response => {
            if (!response.ok) throw new Error(`Failed to load fieldInfo for id=${fieldId}`);
            return response.json();
        })
        .then(data => {
            console.info("FieldInfo loaded with output=%o", data);
            fieldInfoHistory.set(fieldId, data);
            fillFieldPanel(data)
            refreshColor(data.fieldId)
        })
        .catch(err => console.error(`Error loading fieldInfo for id=%o: %o`, fieldId, err));
}

/**
 * Populates the field info panel DOM inputs with values from the given field info object.
 *
 * @param {object} fieldInfo - The field info payload from the API.
 * @param {number} fieldInfo.surfaceArea - Surface area in square meters.
 * @param {string} fieldInfo.grainType - Grain type identifier.
 * @param {string} fieldInfo.plantDate - ISO date string of planting date.
 * @param {string} fieldInfo.expectedHarvestDate - ISO date string of expected harvest date.
 * @param {number} fieldInfo.humidity - Humidity percentage.
 * @param {number} fieldInfo.windSpeed - Wind speed in km/h.
 * @param {string} fieldInfo.fieldName - Human-readable name of the field.
 */
function fillFieldPanel(fieldInfo) {
    console.debug(`Filling field panel with data=%o`, fieldInfo)
    document.getElementById("surfaceArea").value = fieldInfo.surfaceArea ?? "";
    document.getElementById("grainType").value = fieldInfo.grainType ?? "";
    document.getElementById("plantDate").value = new Date(fieldInfo.plantDate).toISOString().substring(0, 10);
    document.getElementById("expectedHarvestDate").value = new Date(fieldInfo.expectedHarvestDate).toISOString().substring(0, 10);
    document.getElementById("humidity").value = fieldInfo.humidity ?? "";
    document.getElementById("windSpeed").value = fieldInfo.windSpeed ?? "";
    document.getElementById("fieldName").value = fieldInfo.fieldName ?? "";
}

/**
 * Resets all field info panel inputs to their empty or default values.
 */
function clearFieldPanel() {
    console.debug(`Clearing field panel`)
    document.getElementById("surfaceArea").value = "";
    document.getElementById("grainType").value = "";
    document.getElementById("plantDate").value = "";
    document.getElementById("expectedHarvestDate").value = "";
    document.getElementById("humidity").value = "0";
    document.getElementById("windSpeed").value = "0";
    document.getElementById("fieldName").value = "";
}

/**
 * Determines whether to save or update a polygon after it is finished
 * drawing or deselected, then records the state in history.
 *
 * @param {string} featureId - The TerraDraw feature ID.
 */
function addOrUpdateOnFinishOrDeselect(featureId) {
    console.debug(`Entering addOrUpdateOnFinishOrDeselect() with featureId=%o`, featureId)
    let feature = draw.getSnapshotFeature(featureId)
    checkDataBaseForPolygon(featureId).then(value => {
        console.debug(`Database existence check for featureId=%o: %o`, featureId, value)
        if (value) updatePolygon(feature)
        else savePolygon(feature)
    })
    addToHistory(featureId, structuredClone(feature))
}

/**
 * Checks whether a polygon with the given ID already exists in the database.
 *
 * @async
 * @param {string} id - The field ID to check.
 * @returns {Promise<boolean>} Resolves to true if the field exists, false otherwise.
 */
async function checkDataBaseForPolygon(id) {
    console.debug(`Checking database for polygon with id=%o`, id)
    const response = await fetch(`/api/fields/exists/${id}`)
    return response.json();
}

/**
 * Persists a new polygon to the API, then creates its associated field info record.
 *
 * @param {object} feature - The GeoJSON feature to save.
 * @param {object|null} [fieldInfo=null] - Optional existing field info to restore;
 *        if null, default values are used when creating the field info record.
 */
function savePolygon(feature, fieldInfo = null) {
    console.debug(`Entering savePolygon() with input=%o`, feature)

    fetch("/api/fields", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(feature)
    })
        .then(response => {
            if (!response.ok) throw new Error(`Failed to save polygon with id=${feature.id}`);
            return response.json();
        })
        .then(data => {
            console.info(`Polygon saved with output=%o`, data);
            createFieldInfo(feature, fieldInfo)
        })
        .catch(err => console.error(`Error saving polygon id=%o: %o`, feature.id, err));
}

/**
 * Creates a field info record for the given feature via the API.
 * Uses default values for all fields unless an existing
 * {@link fieldInfo} object is provided.
 *
 * @param {object} feature - The GeoJSON feature the info belongs to.
 * @param {object|null} [fieldInfo=null] - Optional existing field info to restore.
 *        If null, defaults are applied (grain type: "DEFAULT", dates: today, name: "Unnamed").
 */
function createFieldInfo(feature, fieldInfo = null) {
    console.debug(`Entering createFieldInfo() with input=%o`, feature)

    let basicFieldInfoStructure = {
        "fieldId": feature.id,
        "surfaceArea": fieldInfo === null ? calculateArea(feature) : fieldInfo.surfaceArea,
        "grainType": fieldInfo === null ? "DEFAULT" : fieldInfo.grainType,
        "plantDate": fieldInfo === null ? new Date().toLocaleDateString('en-CA') : fieldInfo.plantDate,
        "expectedHarvestDate": fieldInfo === null ? new Date().toLocaleDateString('en-CA') : fieldInfo.expectedHarvestDate,
        "fieldName": fieldInfo == null ? "Unnamed" : fieldInfo.fieldName
    }

    fetch("/api/fieldInfo", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(basicFieldInfoStructure)
    })
        .then(response => {
            if (!response.ok) throw new Error(`Failed to create fieldInfo for feature id=${feature.id}`);
            return response.json();
        })
        .then(data => {
            console.info(`Created basic fieldInfo with output=%o`, data);
        })
        .catch(err => console.error(`Error creating fieldInfo for feature id=%o: %o`, feature.id, err));
}

/**
 * Sends an updated polygon geometry to the API, then triggers a field info update.
 *
 * @param {object} feature - The GeoJSON feature with updated geometry.
 */
function updatePolygon(feature) {
    console.debug(`Entering updatePolygon() with input=%o`, feature)
    fetch(`/api/fields/${feature.id}`, {
        method: "PUT",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(feature)
    })
        .then(response => {
            if (!response.ok) throw new Error(`Failed to update polygon with id=${feature.id}`);
            return response.json();
        })
        .then(data => {
            console.info(`Polygon updated with output=%o`, data);
            updateFieldInfo(feature.id)
        })
        .catch(err => console.error(`Error updating polygon id=%o: %o`, feature.id, err));
}

/**
 * Reads current field info form values and sends a full update to the API
 * for the given feature ID. Also refreshes the grain color map entry on success.
 *
 * Does nothing if no feature is currently selected.
 *
 * @param {string} featureId - The TerraDraw feature ID whose info should be updated.
 */
function updateFieldInfo(featureId) {
    console.debug(`Entering updateFieldInfo() with featureId=%o`, featureId)
    if (selectedFeatureId === null) {
        console.warn(`Couldn't update FieldInfo: no field is currently selected`)
        return
    }

    let feature = draw.getSnapshotFeature(featureId);
    let updateInfoStructure = {
        "fieldId": feature.id,
        "surfaceArea": calculateArea(feature),
        "grainType": document.getElementById("grainType").value,
        "plantDate": document.getElementById("plantDate").value,
        "expectedHarvestDate": document.getElementById("expectedHarvestDate").value,
        "fieldName": document.getElementById("fieldName").value
    }

    fetch(`/api/fieldInfo/${feature.id}`, {
        method: "PUT",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(updateInfoStructure)
    })
        .then(response => {
            if (!response.ok) throw new Error(`Failed to update fieldInfo for id=${feature.id}`);
            return response.json();
        })
        .then(data => {
            console.info(`Updated fieldInfo with output=%o`, data);
            grainMap.set(data.fieldId, data.grainType)
            loadFieldInfo(feature.id)
        })
        .catch(err => console.error("Error updating fieldInfo for id=%o: %o", feature.id, err));
}

/**
 * Sets the active TerraDraw interaction mode.
 *
 * @param {string} mode - The mode name to activate (e.g., select, polygon).
 */
function setMode(mode) {
    console.info(`Changed user mode to: %o`, mode)
    draw.setMode(mode);
}

/**
 * Fetches all persisted field polygons from the API as GeoJSON features,
 * adds them to the TerraDraw canvas, records them in history and the grain map,
 * then centers the map on any focused field.
 */
function loadPolygons(){
    console.debug(`Loading all polygons from API`)
    fetch("/api/geojsons")
        .then(response => response.json())
        .then(features => {
            features = features.dtoList;
            console.debug(`Loaded %o polygon(s) from API`, features.length)
            draw.addFeatures(features)
            features.forEach(feature => {
                addToHistory(feature.id, structuredClone(feature))
                grainMap.set(feature.id, feature.grainType)
            })
            setMapCenter(getFocusedFieldCenter())
        })
        .catch(err => console.error(`Error loading polygons: %o`, err))
}

/**
 * Deletes the currently selected polygon from the API and removes it from
 * the TerraDraw canvas. Records the deleted state in history for potential undo.
 * Logs a warning if no polygon is currently selected.
 */
function deleteSelectedPolygon(){
    if (selectedFeatureId){
        let tempClone = structuredClone(draw.getSnapshotFeature(selectedFeatureId))
        console.debug(`Deleting selected polygon with id=%o`, selectedFeatureId)
        let id = selectedFeatureId
        draw.deselectFeature(selectedFeatureId)
        fetch(`/api/fields/${id}`, {
            method: 'DELETE'
            }).then(response => {
                if (!response.ok){
                    console.error(`Failed to delete polygon from database with id=%o`, id)
                    return;
                }
                console.info(`Deleted polygon from database with id=%o`, id)
                draw.removeFeatures([id])
                addToHistory(id, tempClone)
            })
            .catch(err => console.error(`Error deleting polygon with id=%o: %o`, id, err));
    } else {
        console.warn(`deleteSelectedPolygon() called with no polygon selected`)
    }
}

/**
 * Calculates the area of the given GeoJSON feature in square meters using Turf.js.
 *
 * @param {object} feature - A GeoJSON feature with a polygon geometry.
 * @returns {number} The area in square meters.
 */
function calculateArea(feature) {
    const area = turf.area(feature)
    console.debug(`Calculated area for id=%o: %o m^2`, feature.id, area)
    return area
}

/**
 * Reverts the most recently modified feature to its previous geometry state
 * by reading from {@link historyToUndo} and {@link historySequence}.
 *
 * Only supports "undo" in current session. Logs an error if the history
 * is empty or no change is available to undo.
 *
 * @param {string} [type="undo"] - The history operation type.
 */
function updateFromHistory(type = "undo") {
    console.debug(`Entering updateFromHistory() with type=%o`, type)
    setMode("select")
    let previous = null
    if (type === "undo" && historySequence.length !== 0) {
        let featureId = historySequence.at(-1)
        let tempArray = structuredClone(historyToUndo.get(featureId))
        if (!draw.hasFeature(featureId)) {
            historySequence.pop()
            previous = tempArray.pop()
        } else if (tempArray.length > 1) {
            historySequence.pop()
            previous = tempArray.pop()
            previous = tempArray.at(-1)
        } else {
            console.error(`History to type=%o is not empty but nothing changed`, type)
            console.debug(`historyToUndo object=%o`, historyToUndo)
            return
        }
    } else {
        console.error(`History to type=%o is empty`, type)
        if (type === "undo") console.debug(`historyToUndo object=%o`, historyToUndo)
        return
    }
    updateFeatureWhenNotNull(previous)
}

/**
 * Sends a surface-area-only update for the given feature's field info to the API.
 * Used after an undo operation restores a previous geometry.
 *
 * @param {object} feature - The GeoJSON feature whose surface area should be recalculated and saved.
 */
function updateOnlySurfaceAreaOfFieldInfo(feature) {
    console.debug(`Updating surface area for feature id=%o`, feature.id)
    let updateSurfaceAreaStructure = {
        "fieldId": feature.id,
        "surfaceArea": calculateArea(feature)
    }

    fetch(`/api/fieldInfo/${feature.id}`, {
        method: "PUT",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(updateSurfaceAreaStructure)
    })
        .then(response => {
            if (!response.ok) throw new Error(`Failed to update surface area for id=${feature.id}`);
            return response.json();
        })
        .then(data => {
            console.info(`Updated fieldInfo surface are with output=%o`, data);
        })
        .catch(err => console.error(`Error updating surface area for id=%o: %o`, feature.id, err));
}

/**
 * Applies a history snapshot feature back onto the map and database if valid.
 * Helper for {@link updateFromHistory}.
 *
 * If the feature already exists on the canvas it is replaced; otherwise it is
 * re-added and its ID is pushed back onto {@link historySequence}.
 *
 * @param {object|null} feature - The history snapshot feature to restore,
 *        or null/undefined if the check failed.
 */
function updateFeatureWhenNotNull(feature) {
    if (feature !== null && feature !== undefined) {
        console.debug(`Restoring history snapshot for feature=%o`, feature)
        updateHistory(feature.id)
        if (draw.hasFeature(feature.id)) draw.removeFeatures([feature.id])
        else historySequence.push(feature.id)
        draw.addFeatures([feature])
        checkDataBaseForPolygon(feature.id).then(value => {
            if (value) {
                updatePolygon(feature)
                updateOnlySurfaceAreaOfFieldInfo(feature)
            }
            else {
                savePolygon(feature, fieldInfoHistory.get(feature.id))
            }
        })
    } else
        console.error(`updateFeatureWhenNotNull() called with null or undefined feature`)
}

/**
 * Removes the most recent history entry for the given feature ID,
 * keeping at least one entry in place.
 *
 * @param {string} featureId - The feature ID whose history should be trimmed.
 */
function updateHistory(featureId) {
    console.debug(`Trimming history for featureId=%o`, featureId)
    let newHistoryChange = historyToUndo.get(featureId);
    if (newHistoryChange.length !== 1) newHistoryChange.pop()
    historyToUndo.set(featureId, newHistoryChange)
}

/**
 * Appends a geometry snapshot to the history for the given feature.
 * Skips the append if the latest snapshot is geometrically identical
 * to the incoming one, preventing duplicate history entries.
 *
 * @param {string} featureId - The feature ID to record history for.
 * @param {object} feature - A deep clone of the current feature state.
 */
function addToHistory(featureId, feature) {
    if (historyToUndo.has(featureId)){
        if (JSON.stringify(historyToUndo.get(featureId).at(-1).geometry) === JSON.stringify(feature.geometry)) {
            console.debug(`Skipping duplicate history entry for featureId=%o`, featureId)
            return
        }
    }

    console.debug(`Adding history entry for featureId=%o`, featureId)
    historySequence.push(featureId)
    if (historyToUndo.has(featureId)) {
        let array = historyToUndo.get(featureId)
        array.push(feature)
        historyToUndo.set(featureId, array)
    } else {
        historyToUndo.set(featureId, [feature])
    }
}

/**
 * Reads the focused field ID from the DOM and returns the geographic center
 * of that field's polygon as a lat/lng object. Also selects the feature on
 * the canvas. Returns undefined if the field is not found on the canvas.
 *
 * @returns {{lat: number, lng: number}|undefined} The center coordinates,
 *          or undefined if the focused field is not present in TerraDraw.
 */
function getFocusedFieldCenter() {
    let val = document.getElementById("focusedFieldId").value;
    console.debug(`Getting center for focusedFieldId=%o`, val)
    let feature = draw.getSnapshotFeature(val);
    let latLng
    if (feature !== undefined) {
        let coordinatesList = feature.geometry.coordinates[0];
        let coordinates = getCenterFromArray(coordinatesList);
        console.debug(`Calculated center coordinates=%o`, coordinates)
        latLng = {
            "lng": coordinates[0],
            "lat": coordinates[1]
        }
        draw.selectFeature(feature.id);
    } else {
        console.warn(`No feature found for focusedFieldId=%o, skipping center`, val)
    }
    return latLng;
}

/**
 * Pans the map to the given lat/lng coordinates.
 *
 * @param {{lat: number, lng: number}} latLng - The target center coordinates.
 */
function setMapCenter(latLng) {
    console.debug(`Setting map center to=%o`, latLng)
    map.setCenter(latLng)
}

/**
 * Returns the current map center as a plain lat/lng object.
 *
 * @returns {{lat: number, lng: number}} The current map center coordinates.
 */
function getCurrentMapCenter() {
    let center = map.getCenter();
    return {
        "lng": center.lng(),
        "lat": center.lat()
    }
}

/**
 * Persists the current map center to the API settings and updates
 * the local {@link savedMapCenter} reference on success.
 */
function updateCenter() {
    let center = getCurrentMapCenter();
    console.debug(`Updating settings center to=%o`, center)
    fetch(`/api/settings`, {
        method: "PUT",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(center)
    })
        .then(response => {
            if (!response.ok) throw new Error(`Failed to update settings center`);
            return response.json();
        })
        .then(data => {
            console.info(`Updated settings center point with output=%o`, data);
            savedMapCenter = data.center
        })
        .catch(err => console.error(`Error updating settings center: %o`, err));
}

/**
 * Calculates the center point of any provided polygon array consisted of
 * [longitude, latitude] coordinate pairs.
 * Helper for {@link getFocusedFieldCenter}.
 *
 * @param {number[][]} arr - Array of {@code [lng, lat]} coordinate pairs.
 * @returns {number[]} A two-element array {@code [centerLng, centerLat]}.
 */
function getCenterFromArray(arr) {
    let x = arr.map (function (a){ return a[0] });
    let y = arr.map (function (a){ return a[1] });
    let minX = Math.min.apply (null, x);
    let maxX = Math.max.apply (null, x);
    let minY = Math.min.apply (null, y);
    let maxY = Math.max.apply (null, y);
    return [(minX + maxX) / 2, (minY + maxY) / 2];
}

/**
 * Resolves the fill/outline color for a given TerraDraw feature ID
 * by looking up its grain type in {@link grainMap} and the corresponding
 * color in {@link colorMap}. Falls back to "#00AAFF" if unmapped.
 *
 * @param {string} id - The TerraDraw feature ID.
 * @returns {string} A hex color string.
 */
function pickColor(id) {
    let grain = grainMap.get(id)
    if (grain === undefined) return "#00aaff";
    return colorMap.get(grain);
}

/**
 * Forces a visual refresh of the polygon for the given feature ID by
 * removing and re-adding it to TerraDraw, then re-selecting it.
 * Used to apply color changes after a grain type update.
 *
 * @param {string} id - The TerraDraw feature ID to refresh.
 */
function refreshColor(id) {
    console.debug(`Refreshing color for feature id=%o`, id)
    let feature = draw.getSnapshotFeature(id)
    draw.removeFeatures([id])
    draw.addFeatures([feature]);
    draw.selectFeature(id);
}

/**
 * Fetches application settings from the API and applies them locally —
 * updating {@link savedMapCenter} and rebuilding {@link colorMap} from
 * the returned grain color configuration.
 *
 * @async
 * @returns {Promise<void>}
 */
async function loadSettings() {
    console.debug(`Loading application settings from API`)
    fetch("/api/settings")
        .then(response => {
            if (!response.ok) throw new Error(`Failed to load settings`);
            return response.json()
        })
        .then(settings => {
            console.info(`Settings loaded: %o`, settings)
            savedMapCenter = settings.center
            setMapCenter(savedMapCenter)
            colorMap = new Map(Object.entries(settings.grainColors))
        })
        .catch(err => console.error(`Error loading settings: %o`, err))
}

/** Keyboard shortcuts */
window.addEventListener("keydown", (e) => {
    if (e.key === "Delete") deleteSelectedPolygon()
    if (e.ctrlKey && e.key === "z") updateFromHistory()
})