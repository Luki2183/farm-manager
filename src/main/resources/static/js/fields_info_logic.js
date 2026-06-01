async function deleteField(btn) {
    const fieldId = btn.dataset.fieldId;
    console.debug(`Deleting polygon with id=%o`, fieldId)
    await fetch(`/api/fields/${fieldId}`, {
        method: 'DELETE'
    }).then(response => {
        if (!response.ok){
            console.error(`Failed to delete polygon from database with id=%o`, fieldId)
            return;
        }
        console.info(`Deleted polygon from database with id=%o`, fieldId)
    }).catch(err => console.error(`Error deleting polygon with id=%o: %o`, fieldId, err));
    location.reload()
}