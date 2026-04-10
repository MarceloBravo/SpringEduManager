// Función específica para eliminar curso
const eliminarClick = (id) => {
    showModal(
        'deleteForm',
        id,
        'Eliminar curso',
        '¿Desea eliminar el curso?',
        'Eliminar'
    );
}



const grabarClick = (id) => {
    showModal(
        'saveForm',
        id,
        'Guardar curso',
        '¿Desea guardar el curso?',
        'Guardar'
    );
}
