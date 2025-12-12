async function init() {
    const { Map3DElement, MapMode, Model3DElement } = await google.maps.importLibrary("maps3d");

    const map = new Map3DElement({
        center: {lat: 37.7438, lng: -121.5088, altitude: 1800},
        heading: -90,
        tilt: 90,
        mode: MapMode.SATELLITE,
    });

    document.body.append(map);

    const models = [
        // A model with regular settings.
        {
            position: {lat: 37.76, lng: -121.63, altitude: 0},
            orientation: {tilt: 270},
        },
        // Scaled down model.
        {
            position: {lat: 37.75, lng: -121.63, altitude: 0},
            orientation: {tilt: 270},
            scale: 0.8,
        },
        // Scaled up model.
        {
            position: {lat: 37.735, lng: -121.63, altitude: 0},
            orientation: {tilt: 270},
            scale: 1.2,
        },
        // A model with an additional transformation applied.
        {
            position: {lat: 37.72, lng: -121.63, altitude: 0},
            orientation: {tilt: 270, roll: 90},
        },
        // Non-clamped to the ground model.
        {
            position: {lat: 37.71, lng: -121.63, altitude: 1000},
            altitudeMode: 'RELATIVE_TO_GROUND',
            orientation: {tilt: 270},
        },
    ];

    for (const {position, altitudeMode, orientation, scale} of models) {
        const model = new Model3DElement({
            src: 'https://storage.googleapis.com/maps-web-api.appspot.com/gltf/windmill.glb',
            position,
            altitudeMode,
            orientation,
            scale,
        });

        map.append(model);
    }
}

init();
