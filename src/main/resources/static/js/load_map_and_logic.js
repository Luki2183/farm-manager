let map;
let draw;
let selectedFeatureId = null;
let geometryCopyOfSelected = null
const historyToUndo = new Map()
const historySequence = []

async function initMap() {
    map = new google.maps.Map(document.getElementById("map"), {
        center: { lat: 52.2297, lng: 21.0122 },
        zoom: 12,
    });

    google.maps.event.addListenerOnce(map, "projection_changed", () =>{
        draw = new terraDraw.TerraDraw({
            adapter: new terraDrawGoogleMapsAdapter.TerraDrawGoogleMapsAdapter({
                lib: google.maps,
                map: map
            }),
            modes: [
                new terraDraw.TerraDrawPolygonMode({
                    snapping: {
                        toLine: true,
                        toCoordinate: true,
                    }
                }),
                new terraDraw.TerraDrawSelectMode({
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
        })
        draw.on("deselect", () => {
            console.info("Deselected feature with id=%o", selectedFeatureId)
            let change = JSON.stringify(geometryCopyOfSelected) !== JSON.stringify(draw.getSnapshotFeature(selectedFeatureId).geometry)
            if (change) addOrUpdateOnFinishOrDeselect(selectedFeatureId)
            selectedFeatureId = null;
            geometryCopyOfSelected = null
        })
        draw.on("finish", (featureId) => {
            addOrUpdateOnFinishOrDeselect(featureId)
        })

        loadPolygons()
    })

}

function addOrUpdateOnFinishOrDeselect(featureId) {
    let feature = draw.getSnapshotFeature(featureId)
    savePolygon(feature)
    addToHistory(featureId, structuredClone(feature))
}

function savePolygon(feature) {
    console.debug("Entering savePolygon(feature) with input=%o", feature)

    feature['area'] = calculateArea(feature)

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
            })
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

// Helper function to updateFromHistory(type)
function updateFeatureWhenNotNull(feature) {
    if (feature !== null && feature !== undefined) {
        console.debug("Successful check for update conditions with input=%o", feature)
        updateHistory(feature.id)
        if (draw.hasFeature(feature.id)) draw.removeFeatures([feature.id])
        else historySequence.push(feature.id)
        draw.addFeatures([feature])
        savePolygon(feature)
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

// Shortcuts
window.addEventListener("keydown", (e) => {
    if (e.key === "Delete") deleteSelectedPolygon()
    if (e.ctrlKey && e.key === "z") updateFromHistory()
})