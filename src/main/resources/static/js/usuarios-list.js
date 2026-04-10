// Función específica para eliminar usuario
const eliminarUsuarioClick = (id) => {
    showModal(
        'deleteForm',
        id,
        'Eliminar usuario',
        '¿Desea eliminar el usuario?',
        'Eliminar'
    );
}
