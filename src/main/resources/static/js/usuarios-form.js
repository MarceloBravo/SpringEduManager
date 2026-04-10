const eliminarClick = (id) => {
    showModal(
        'deleteForm',
        id,
        'Eliminar usuario',
        '¿Desea eliminar el usuario?',
        'Eliminar'
    );
}

const grabarClick = (id) => {
    showModal(
        'saveForm',
        id,
        'Guardar usuario',
        '¿Desea guardar el usuario?',
        'Guardar'
    );
}
