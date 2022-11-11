$(function () {
    let token = $("meta[name='_csrf']").attr("content"),
        header = $("meta[name='_csrf_header']").attr("content");
    $(document).ajaxSend(function(e, xhr, options) {
        xhr.setRequestHeader(header, token);
    });
    $('.sidenav').sidenav();
});

function updateOrderNumbersInView($table) {
    $table.find('tbody tr').each(function(i,tr) {
        $(tr).children('td').eq(0).text(i + 1);
    });
}

function incrementBadgeNumber($badge) {
    let currentValue = parseInt($badge.text());
    $badge.text(currentValue + 1);
}