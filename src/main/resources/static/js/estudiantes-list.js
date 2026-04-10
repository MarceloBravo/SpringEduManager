// Función específica para eliminar estudiante
const eliminarEstudianteClick = (id) => {
    showModal(
        'deleteForm',
        id,
        'Eliminar estudiante',
        '¿Desea eliminar el estudiante?',
        'Eliminar'
    );
}