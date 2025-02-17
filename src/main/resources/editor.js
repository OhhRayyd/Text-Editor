var editor = CodeMirror.fromTextArea(document.getElementById("editor"), {
    lineNumbers: true,
    mode: "javascript",
    theme: "dracula", // You can change the theme
    autoCloseBrackets: true,
    lineWrapping: true
});


document.getElementById("saveButton").addEventListener("click", function() {
    let text = editor.getValue();
    if(window.javaConnector){
        window.javaConnector.javafxSaveFile(text);
    } else {
        console.log("JavaConnector not found");
    }
});

document.getElementById("loadButton").addEventListener("click", function() {
    if(window.javaConnector){
    window.javaConnector.javafxLoadFile();
}
    else {
        console.log("JavaConnector not found");
    }
});

