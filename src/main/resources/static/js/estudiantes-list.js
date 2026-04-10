tailwind.config = {
    darkMode: "class",
    theme: {
        extend: {
            "colors": {
                "surface-container-low": "#f1f4f5",
                "error": "#a83836",
                "secondary": "#50616f",
                "secondary-fixed-dim": "#c5d7e7",
                "on-secondary-fixed": "#31414e",
                "on-tertiary-container": "#40446c",
                "surface-container-highest": "#dee3e6",
                "tertiary-container": "#cbcefe",
                "on-secondary-container": "#435461",
                "primary-fixed-dim": "#0060e2",
                "surface-container-high": "#e5e9eb",
                "secondary-dim": "#445563",
                "on-error-container": "#6e0a12",
                "on-secondary-fixed-variant": "#4d5d6b",
                "error-dim": "#67040d",
                "on-primary-container": "#ffffff",
                "primary": "#0058cf",
                "tertiary": "#585c85",
                "surface-container": "#ebeef0",
                "surface-variant": "#dee3e6",
                "error-container": "#fa746f",
                "surface-tint": "#0058cf",
                "on-tertiary-fixed": "#2d3157",
                "surface-container-lowest": "#ffffff",
                "on-secondary": "#f5f9ff",
                "surface": "#f8f9fa",
                "inverse-surface": "#0c0f10",
                "tertiary-fixed": "#cbcefe",
                "outline-variant": "#adb3b5",
                "secondary-container": "#d3e5f5",
                "on-error": "#fff7f6",
                "on-primary-fixed-variant": "#e2e7ff",
                "background": "#f8f9fa",
                "primary-fixed": "#036cfb",
                "secondary-fixed": "#d3e5f5",
                "on-surface-variant": "#5a6062",
                "tertiary-dim": "#4c5078",
                "on-primary": "#f9f8ff",
                "inverse-on-surface": "#9b9d9e",
                "tertiary-fixed-dim": "#bdc0ef",
                "on-tertiary": "#fbf8ff",
                "surface-bright": "#f8f9fa",
                "on-background": "#2d3335",
                "on-surface": "#2d3335",
                "outline": "#767c7e",
                "primary-container": "#036cfb",
                "on-primary-fixed": "#ffffff",
                "primary-dim": "#004cb7",
                "on-tertiary-fixed-variant": "#494e76",
                "surface-dim": "#d5dbdd",
                "inverse-primary": "#588cff"
            },
            "borderRadius": {
                "DEFAULT": "0.125rem",
                "lg": "0.25rem",
                "xl": "0.5rem",
                "full": "0.75rem"
            },
            "fontFamily": {
                "headline": ["Manrope"],
                "body": ["Inter"],
                "label": ["Inter"]
            }
        },
    },
}

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