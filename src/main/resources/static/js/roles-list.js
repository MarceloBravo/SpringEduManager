// Función específica para eliminar estudiante
const eliminarRolClick = (id) => {
    showModal(
        'deleteForm',
        id,
        'Eliminar rol',
        '¿Desea eliminar el rol?',
        'Eliminar'
    );
}