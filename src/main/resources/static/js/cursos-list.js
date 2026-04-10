// Función específica para eliminar curso
const eliminarCursoClick = (id) => {
    showModal(
        'deleteForm',
        id,
        'Eliminar curso',
        '¿Desea eliminar el curso?',
        'Eliminar'
    );
}
