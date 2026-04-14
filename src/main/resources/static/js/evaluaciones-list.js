
// ************* Funciones para manejo de Cursos ****************** // 
const showModalCursos = () => {
    const modal = document.getElementById("modalCursos");
    modal.style.display = "block";
    document.getElementById("busquedaCurso").value = "";
    document.querySelector('#modalCursos ul').innerHTML = "";
}

const hideModalCursos = () => {
    const modal = document.getElementById("modalCursos");
    modal.style.display = "none";
}

const inscribirCurso = async (id) => {
    const alumnoId = document.getElementById("estudianteId").value;
    
    try {
        const resp = await fetch(`/estudiantes-cursos/grabar`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                estudianteId: alumnoId,
                cursoId: id
            })
        });
        
        const result = await resp.json();
        
        if (result.error) {
            showToastJs('Error', result.error, 'danger');
        } else {
            showToastJs('Info', 'Curso inscrito exitosamente', 'success');
            window.location.reload();
        }
    } catch (error) {
        showToastJs('Error', 'Error al inscribir curso', 'danger');
        console.error('Error al inscribir curso:', error);
    }
};

const buscarCursos = async (texto) => {
    try {
        const response = await fetch(`/cursos/js-search?filtro=${texto}`, {
            method: 'GET'
        });
        const cursos = await response.json();
        
        const ul = document.querySelector('#modalCursos ul');
        ul.innerHTML = cursos.map(curso => 
            `<li ondblclick="seleccionarCurso(${curso.id}, '${curso.nombre}')" style="cursor: pointer;" title="Haga doble clic para matricular">
                ${curso.nombre}
            </li>`
        ).join('');
    } catch (error) {
        showToastJs('Error', 'Error buscando cursos', 'danger');
    }
};

const seleccionarCurso = (id, nombre) => {
    hideModalCursos();
    inscribirCurso(id);
}

const deleteCurso = async (id) => {
    try{
        const response = await fetch(`/estudiantes-cursos/eliminar/${id}`, {
            method: 'POST'
        });
        const result = await response.json();
        
        if (result.error) {
            showToastJs('Error', result.error, 'danger');
        } else {
            showToastJs('Info', 'Curso eliminado exitosamente', 'success');
            setTimeout(() => {
                window.location.reload();
            }, 5000);
        }
    } catch (error) {
        showToastJs('Error', 'Error al eliminar curso', 'danger');
        console.error('Error al eliminar curso:', error);
    }
}



// **************** Funciones para manejo de Notas ****************** //
const showModalNotas = (cursoId) => {
    const modal = document.getElementById("modalNotas");
    modal.style.display = "block";
    document.getElementById("nota").value = "";
    document.getElementById("cursoId").value = cursoId;
}

const hideModalNotas = () => {
    const modal = document.getElementById("modalNotas");
    modal.style.display = "none";
}

const grabarEvaluacion = async () => {
    const cursoId = document.getElementById("cursoId").value;
    const alumnoId = document.getElementById("estudianteId").value;
    const nota = document.getElementById("nota").value;
    
    try {
        const resp = await fetch(`/cursos-evaluaciones/grabar`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                estudianteId: alumnoId,
                cursoId: cursoId,
                nota
            })
        });
        const result = await resp.json();
        
        if (result.error) {
            showToastJs('Error', result.error, 'danger');
        } else {
            hideModalNotas();
            showToastJs('Info', 'Evaluación grabada exitosamente', 'success');
            setTimeout(() => {
                window.location.reload();
            }, 5000);
        }
    } catch (error) {
        console.error('Error al grabar evaluación:', error);
        showToastJs('Error', 'Error de conexión al servidor', 'danger');
    }
};


const eliminarEvaluacion = async (id) => {
    if(id === null || id === undefined){
        return;
    }
    try{
        const response = await fetch(`/cursos-evaluaciones/eliminar/${id}`, {
            method: 'POST'
        });
        const result = await response.json();
        
        if (result.error) {
            showToastJs('Error', result.error, 'danger');
        } else {
            showToastJs('Info', 'Evaluación eliminada exitosamente', 'success');
            setTimeout(() => {
                window.location.reload();
            }, 5000);
        }
    } catch (error) {
        showToastJs('Error', 'Error al eliminar evaluación', 'danger');
        console.error('Error al eliminar evaluación:', error);
    }
}