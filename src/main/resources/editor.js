var editor = CodeMirror.fromTextArea(document.getElementById("editor"), {
    lineNumbers: true,
    mode: "javascript",
    theme: "dracula", // You can change the theme
    autoCloseBrackets: true,
    lineWrapping: true
});
