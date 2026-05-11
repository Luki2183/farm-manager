let map;
let draw;
let selectedFeatureId = null;
let geometryCopyOfSelected = null
const historyToUndo = new Map()
const fieldInfoHistory = new Map()
const historySequence = []

let savedMapCenter = null;

let colorMap = new Map;

const grainMap = new Map;

async function initMap() {
    await loadSettings()
    map = new google.maps.Map(document.getElementById("map"), {
        center: savedMapCenter,
        zoom: 15,
        streetViewControl: false
    });
    google.maps.event.addListenerOnce(map, "projection_changed", () =>{
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
                        toLine: true,
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
            ],
            // idStrategy: {
            //     isValidId: (id) => typeof id === "number" && Number.isInteger(id),
            //     getId: (function () {
            //         let id = 0;
            //         return function () {
            //             return ++id;
            //         };
            //     })()}
        });

        draw.start();
        prepareButtonsVisual()
        setMode("select")
        draw.on("select", (featureId) => {
            console.info("Selected feature with id: ", featureId)
            geometryCopyOfSelected = structuredClone(draw.getSnapshotFeature(featureId).geometry)
            selectedFeatureId = featureId;
            loadFieldInfo(featureId)
        })
        draw.on("deselect", () => {
            console.info("Deselected feature with id=%o", selectedFeatureId)
            let change = JSON.stringify(geometryCopyOfSelected) !== JSON.stringify(draw.getSnapshotFeature(selectedFeatureId).geometry)
            if (change) addOrUpdateOnFinishOrDeselect(selectedFeatureId)
            selectedFeatureId = null;
            geometryCopyOfSelected = null
            clearFieldPanel()
        })
        draw.on("finish", (featureId) => {
            addOrUpdateOnFinishOrDeselect(featureId)
        })

        loadPolygons()
        clearFieldPanel()
        loadSettings()
    })

}

function loadFieldInfo(fieldId) {
    fetch(`api/fieldInfo/${fieldId}`)
        .then(response => {
            if (!response.ok) throw new Error("Failed to load fieldInfo with input=%o", fieldId);
            return response.json();
        })
        .then(data => {
            console.info("FieldInfo loaded with output=%o", data);
            fieldInfoHistory.set(fieldId, data);
            fillFieldPanel(data)
            refreshColor(data.fieldId)
        })
        .catch(err => console.error(err));
}

function fillFieldPanel(fieldInfo) {
    document.getElementById("surfaceArea").value = fieldInfo.surfaceArea ?? "";
    document.getElementById("grainType").value = fieldInfo.grainType ?? "";
    document.getElementById("plantDate").value = new Date(fieldInfo.plantDate).toISOString().substring(0, 10);
    document.getElementById("expectedHarvestDate").value = new Date(fieldInfo.expectedHarvestDate).toISOString().substring(0, 10);
    document.getElementById("humidity").value = fieldInfo.humidity ?? "";
    document.getElementById("windSpeed").value = fieldInfo.windSpeed ?? "";
    document.getElementById("fieldName").value = fieldInfo.fieldName ?? "";
} // todo move logic to backend/replace by thymeleaf

function clearFieldPanel() {
    document.getElementById("surfaceArea").value = "";
    document.getElementById("grainType").value = "";
    document.getElementById("plantDate").value = "";
    document.getElementById("expectedHarvestDate").value = "";
    document.getElementById("humidity").value = "0";
    document.getElementById("windSpeed").value = "0";
    document.getElementById("fieldName").value = "";
}

function addOrUpdateOnFinishOrDeselect(featureId) {
    let feature = draw.getSnapshotFeature(featureId)
    checkDataBaseForPolygon(featureId).then(value => {
        if (value) updatePolygon(feature)
        else savePolygon(feature)
    })
    addToHistory(featureId, structuredClone(feature))
}

async function checkDataBaseForPolygon(id) {
    const response = await fetch(`/api/fields/exists/${id}`)
    return response.json();
}

function savePolygon(feature, fieldInfo = null) {
    console.debug("Entering savePolygon(feature) with input=%o", feature)

    fetch("/api/fields", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(feature)
    })
        .then(response => {
            if (!response.ok) throw new Error("Failed to save polygon with input=%o", feature);
            return response.json();
        })
        .then(data => {
            console.info("Polygon saved with output=%o", data);
            createFieldInfo(feature, fieldInfo)
        })
        .catch(err => console.error(err));
}

function createFieldInfo(feature, fieldInfo = null) {
    console.debug("Entering createFieldInfo(feature) with input=%o", feature)

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
            if (!response.ok) throw new Error("Failed to create basic fieldInfo");
            return response.json();
        })
        .then(data => {
            console.info("Created basic fieldInfo with output=%o", data);
        })
        .catch(err => console.error(err));
}

function updatePolygon(feature) {
    console.debug("Entering updatePolygon(feature) with input=%o", feature)
    fetch(`/api/fields/${feature.id}`, {
        method: "PUT",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(feature)
    })
        .then(response => {
            if (!response.ok) throw new Error("Failed to update polygon with input=%o", feature);
            return response.json();
        })
        .then(data => {
            console.info("Polygon updated with output=%o", data);
            updateFieldInfo(feature.id)
        })
        .catch(err => console.error(err));
}

function updateFieldInfo(featureId) {
    if (selectedFeatureId === null) {
        console.warn("Couldn't update FieldInfo with not selected Field")
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
            if (!response.ok) throw new Error("Failed to update fieldInfo with id=%o", feature.id);
            return response.json();
        })
        .then(data => {
            console.info("Updated fieldInfo with output=%o", data);
            grainMap.set(data.fieldId, data.grainType)
            loadFieldInfo(feature.id)
        })
        .catch(err => console.error(err));
}

function setMode(mode) {
    console.info("Changed user mode to: " + mode)
    draw.setMode(mode);
}

function loadPolygons(){
    fetch("/api/geojsons")
        .then(response => response.json())
        .then(features => {
            console.debug("Entering draw.addFeatures() with input=%o ", features)
            draw.addFeatures(features)
            features.forEach(feature => {
                addToHistory(feature.id, structuredClone(feature))
                grainMap.set(feature.id, feature.grainType)
            })
            setMapCenter(getFocusedFieldCenter())
        })
}

function deleteSelectedPolygon(){
    if (selectedFeatureId){
        let tempClone = structuredClone(draw.getSnapshotFeature(selectedFeatureId))
        console.debug("Entering deleteSelectedPolygon() with current selectedFeatureId=%o", selectedFeatureId)
        let id = selectedFeatureId
        draw.deselectFeature(selectedFeatureId)
        fetch(`/api/fields/${id}`, {
            method: 'DELETE'
        }).then(response => {
            if (!response.ok){
                console.error("Failed to delete polygon from DB with id=%o", id)
                return;
            }
            console.info("Deleted polygon from DB with id=%o ", id)
            draw.removeFeatures([id])
            addToHistory(id, tempClone)
        });
    } else {
        console.warn("Entering deleteSelectedPolygon() without selected polygon")
    }
}

function calculateArea(feature) {
    const area = turf.area(feature)
    console.log("Calculated area for %o, result: %o", feature.id, area)
    return area
}

function updateFromHistory(type = "undo") {
    console.debug("Entering updateFromHistory(type) with input=%o", type)
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
            console.error("History to %o is not empty but nothing changed", type)
            console.debug("History object=%o", historyToUndo)
            return
        }
    } else {
        console.error("History to %o is empty", type)
        if (type === "undo") console.debug("History object=%o", historyToUndo)
        return
    }
    updateFeatureWhenNotNull(previous)
}

function updateOnlySurfaceAreaOfFieldInfo(feature) {
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
            if (!response.ok) throw new Error("Failed to update fieldInfo area with id=%o", feature.id);
            return response.json();
        })
        .then(data => {
            console.info("Updated fieldInfo area with output=%o", data);
        })
        .catch(err => console.error(err));
}

// Helper function to updateFromHistory(type)
function updateFeatureWhenNotNull(feature) {
    if (feature !== null && feature !== undefined) {
        console.debug("Successful check for update conditions with input=%o", feature)
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
        console.debug("Error when checking update conditions with input=%o", feature)
}

function updateHistory(featureId) {
    let newHistoryChange = historyToUndo.get(featureId);
    if (newHistoryChange.length !== 1) newHistoryChange.pop()
    historyToUndo.set(featureId, newHistoryChange)
}

function addToHistory(featureId, feature) {
    // Additional check when double saving to db
    if (historyToUndo.has(featureId)){
        if (JSON.stringify(historyToUndo.get(featureId).at(-1).geometry) === JSON.stringify(feature.geometry)) return
    }

    historySequence.push(featureId)
    let array;
    if (historyToUndo.has(featureId)) {
        array = historyToUndo.get(featureId)
        array.push(feature)
        historyToUndo.set(featureId, array)
    } else {
        historyToUndo.set(featureId, [feature])
    }
}

function getFocusedFieldCenter() {
    let val = document.getElementById("focusedFieldId").value;
    let feature = draw.getSnapshotFeature(val);
    let latLng
    if (feature !== undefined) {
        let coordinatesList = feature.geometry.coordinates[0];
        console.debug(coordinatesList)
        let coordinates = getCenterFromArray(coordinatesList);
        console.debug(coordinates)
        latLng = {
            "lng": coordinates[0],
            "lat": coordinates[1]
        }
        draw.selectFeature(feature.id);
    }
    return latLng;
}

function setMapCenter(latLng) {
    map.setCenter(latLng)
}

function getCurrentMapCenter() {
    let center = map.getCenter();
    return {
        "lng": center.lng(),
        "lat": center.lat()
    }
}

function updateCenter() {
    fetch(`/api/settings`, {
        method: "PUT",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(getCurrentMapCenter())
    })
        .then(response => {
            if (!response.ok) throw new Error("Failed to update fieldInfo with id=%o", feature.id);
            return response.json();
        })
        .then(data => {
            console.info("Updated settings center point with output=%o", data);
            savedMapCenter = data.center
        })
        .catch(err => console.error(err));
}

// Helper function to setMapCenter()
function getCenterFromArray(arr) {
    let x = arr.map (function (a){ return a[0] });
    let y = arr.map (function (a){ return a[1] });
    let minX = Math.min.apply (null, x);
    let maxX = Math.max.apply (null, x);
    let minY = Math.min.apply (null, y);
    let maxY = Math.max.apply (null, y);
    return [(minX + maxX) / 2, (minY + maxY) / 2];
}

function pickColor(id) {
    let grain = grainMap.get(id)
    if (grain === undefined) return "#00aaff";
    return colorMap.get(grain);
}

function refreshColor(id) {
    let feature = draw.getSnapshotFeature(id)
    draw.removeFeatures([id])
    draw.addFeatures([feature]);
    draw.selectFeature(id);
}

async function loadSettings() {
    fetch("/api/settings")
        .then(response => response.json())
        .then(settings => {
            savedMapCenter = settings.center
            setMapCenter(savedMapCenter)
            colorMap = new Map(Object.entries(settings.colorMap))
        })
}

// Shortcuts
window.addEventListener("keydown", (e) => {
    if (e.key === "Delete") deleteSelectedPolygon()
    if (e.ctrlKey && e.key === "z") updateFromHistory()
})