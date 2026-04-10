// Función específica para eliminar estudiante
const eliminarClick = (id) => {
    showModal(
        'deleteForm',
        id,
        'Eliminar estudiante',
        '¿Desea eliminar el estudiante?',
        'Eliminar'
    );
}



const grabarClick = (id) => {
    showModal(
        'saveForm',
        id,
        'Guardar estudiante',
        '¿Desea guardar el estudiante?',
        'Guardar'
    );
}
