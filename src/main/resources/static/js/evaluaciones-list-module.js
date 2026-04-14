/**
 * Maneja el clic en la tabla para mostrar el menú contextual
 */
document.addEventListener('click', (event) => {
  const celda = event.target.closest('td');
  const activeTd = document.querySelector(".active-td");
  if(activeTd){
      activeTd.classList.remove("active-td");
  }

  if (celda && celda.dataset.id != null && celda.dataset.id != undefined){
    const rect = celda.getBoundingClientRect();
    showPopupMenu(celda, rect.left, rect.top);
  }else{
    hidePopupMenu();
  }
});



// La variable cursosSinNotas se define en evaluaciones-list.js
// Si un cursos no tiene notas, muestra la columna eliminar de lo contrario permanece oculta
if(cursosSinNotas > 0) {
      console.log("Cursos con notas: " + cursosSinNotas);
      console.log('Mostrando columna de eliminación');
      if(cursosSinNotas > 0) {                
          document.querySelectorAll('.delete-column').forEach(element => {
              element.style.display = 'table-cell'; 
          });
      }
  }