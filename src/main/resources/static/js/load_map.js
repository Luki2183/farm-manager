let map;
let selectedPolygon = null;

function initMap() {
    console.log("Google Maps loaded:", !!google.maps);

    map = new google.maps.Map(document.getElementById("map"), {
        center: { lat: 52.22, lng: 21.01 },
        zoom: 12,
    });

    fetch("/api/fields")
        .then(response => response.json())
        .then(polygons => {
            polygons.forEach(poly => drawPolygonFromDb(poly));
        });

    const drawingManager = new google.maps.drawing.DrawingManager({
        drawingMode: google.maps.drawing.OverlayType.POLYGON,
        drawingControl: true,
        drawingControlOptions: {
            position: google.maps.ControlPosition.TOP_CENTER,
            drawingModes: [
                google.maps.drawing.OverlayType.POLYGON
            ]
        },
        polygonOptions: {
            fillColor: "#ff0000",
            fillOpacity: 0.5,
            strokeWeight: 2,
            editable: false,
        }
    });

    drawingManager.setMap(map);

    // When polygon drawing is finished
    google.maps.event.addListener(drawingManager, 'polygoncomplete', function (polygon) {
        attachPolygonEvents(polygon, map);
    });

    // Hide menu when clicking map
    map.addListener("click", () => hideContextMenu());
}

function attachPolygonEvents(polygon, map) {
    polygon.addListener("rightclick", function (e) {
        selectedPolygon = polygon;
        showContextMenu(e.domEvent.clientX, e.domEvent.clientY);
    });
}

function showContextMenu(x, y) {
    const menu = document.getElementById("contextMenu");
    menu.style.left = x + "px";
    menu.style.top = y + "px";
    menu.style.display = "block";
}

function hideContextMenu() {
    document.getElementById("contextMenu").style.display = "none";
}

function deletePolygon() {
    if (!selectedPolygon)
        return

    const id = selectedPolygon.dbId;

    if (id){
        fetch(`/api/fields/${id}`, {
            method: 'DELETE'
        }).then(response => {
            if (!response.ok){
                console.error("Failed to delete polygon from DB")
                return;
            }
            console.log("Deleted polygon from DB", id)
        });
    }

    selectedPolygon.setMap(null);

    hideContextMenu();
}

function toggleEditPolygon() {
    if (selectedPolygon) {
        const editable = selectedPolygon.getEditable();
        selectedPolygon.setEditable(!editable);
    }
    hideContextMenu();
}

function savePolygon() {
    if (!selectedPolygon) return;

    const path = selectedPolygon.getPath();
    const coordinates = [];

    for (let i = 0; i < path.getLength(); i++) {
        const p = path.getAt(i);
        coordinates.push({ lat: p.lat(), lng: p.lng() });
    }

    const polygonData = {
        name: "Polygon " + new Date().toISOString(),
        coordinates: coordinates
    };

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
            console.log("Polygon saved:", data);
            alert("Polygon saved!");
            selectedPolygon.dbId = data.id;
        })
        .catch(err => console.error(err));

    hideContextMenu();
}

function drawPolygonFromDb(polygonData) {

    const coords = polygonData.coordinates.map(p => ({
        lat: p.lat,
        lng: p.lng
    }));

    const polygon = new google.maps.Polygon({
        paths: coords,
        map: map,
        fillColor: "#FF0000",
        fillOpacity: 0.35,
        strokeWeight: 2,
        editable: false
    });

    polygon.dbId = polygonData.id;

    // Reattach right-click menu so it behaves like a new polygon
    attachPolygonEvents(polygon, map);
}

window.onload = initMap;