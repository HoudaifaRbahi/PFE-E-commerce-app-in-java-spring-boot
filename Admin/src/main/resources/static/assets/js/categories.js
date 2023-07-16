$('document').ready(function() {
    $('table #editButton').on('click', function (event) {
        event.preventDefault();
        var href = $(this).attr('href');
        $.get(href, function (category, status) {
            $('#idEdit').val(category.id);
            $('#icoonEdit').val(category.icon);
            $('#nameEdit').val(category.name);

        });
        $('#editModal').modal();
    });
});