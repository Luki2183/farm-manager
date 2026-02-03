let map;
let draw;
let selectedFeatureId = null;

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
        draw.on("select", (id) => {
            console.info("Selected feature with id: ", id)
            selectedFeatureId = id;
        })
        draw.on("deselect", () => {
            selectedFeatureId = null;
        })
        draw.on("finish", (feature) => {
            savePolygon(feature)
        })

        loadPolygons()
    })

}

function savePolygon(featureId) {
    console.debug("Entering savePolygon(feature) with input=%o", featureId)

    const field = draw.getSnapshotFeature(featureId)

    field['area'] = calculateArea(field)

    fetch("/api/fields", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(field)
    })
        .then(response => {
            if (!response.ok) throw new Error("Failed to save polygon with input=%o", featureId);
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
        })
}

function deleteSelectedPolygon(){
    if (selectedFeatureId){
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