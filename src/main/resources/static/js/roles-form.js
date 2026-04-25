// Función específica para eliminar rol
const eliminarClick = (id) => {
    showModal(
        'deleteForm',
        id,
        'Eliminar rol',
        '¿Desea eliminar el rol?',
        'Eliminar'
    );
}



const grabarClick = (id) => {
    showModal(
        'saveForm',
        id,
        'Guardar rol',
        '¿Desea guardar el rol?',
        'Guardar'
    );
}
