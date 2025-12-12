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
        draw.setMode('select')
        draw.on("select", (id) => {
            console.log("selected feature with id: ", id)
            selectedFeatureId = id;
        })
        draw.on("deselect", () => {
            console.log("deselected feature");
            selectedFeatureId = null;
        })
        draw.on("finish", (feature) => {
            console.log("Trying to invoke savePolygon with feature id: ", JSON.stringify(feature));
            savePolygon(feature)
        })

        loadPolygons()
    })

}

function savePolygon(feature) {

    console.log("savePolygon function call")

    const field = draw.getSnapshotFeature(feature)

    const coordinates = [];

    for (var array of field.geometry.coordinates[0]){
        coordinates.push({ lat: array[0], lng: array[1] });
    }

    const polygonData = {
        id: field.id,
        coordinates: coordinates
    }

    fetch("/api/fields", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(polygonData)
    })
        .then(response => {
            if (!response.ok) throw new Error("Failed to save polygon");
            return response.json();
        })
        .then(data => {
            console.log("Polygon saved with data: ", data);
        })
        .catch(err => console.error(err));
}

function setMode(mode) {
    console.log("changed mode to: " + mode)
    draw.setMode(mode);
}

function loadPolygons(){
    console.info("loadPolygons function invocation")
    fetch("/api/geojsons")
        .then(response => response.json())
        .then(features => {
            console.info("trying to load features: ", features)
            draw.addFeatures(features)
        })
}

function deleteSelectedPolygon(){
    console.log("deleteSelectedPolygon invocation")
    if (selectedFeatureId){
        let id = selectedFeatureId
        console.log("   trying to delete feature with id: ", selectedFeatureId)
        draw.deselectFeature(selectedFeatureId)
        fetch(`/api/fields/${id}`, {
            method: 'DELETE'
        }).then(response => {
            if (!response.ok){
                console.error("Failed to delete polygon from DB")
                draw.selectFeature(id)
                return;
            }
            console.log("Deleted polygon from DB with id: ", id)
            draw.removeFeatures([id])
        });
    } else {
        console.log("   feature not selected")
    }
}