var form = document.querySelector('.needs-validation');

form.addEventListener('submit', (event) =>{
    if (form.checkValidity() === false){
        event.preventDefault();
        event.stopPropagation();
    }
    form.classList.add('was-validated');
});