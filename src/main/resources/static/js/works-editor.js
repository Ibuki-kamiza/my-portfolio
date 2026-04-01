document.addEventListener('DOMContentLoaded', function() {
    const editorEl = document.getElementById('quill-editor');
    if (!editorEl) return;

    const quill = new Quill('#quill-editor', {
        theme: 'snow',
        modules: {
            toolbar: {
                container: [
                    ['bold', 'italic', 'underline'],
                    [{ 'header': [1, 2, 3, false] }],
                    [{ 'list': 'ordered' }, { 'list': 'bullet' }],
                    ['link', 'image'],
                    ['clean']
                ],
                handlers: {
                    image: function() {
                        const url = prompt('画像URLを入力してください（Google DriveのURLも自動変換します）');
                        if (url) {
                            let imageUrl = url;
                            const driveMatch = url.match(/\/d\/([a-zA-Z0-9_-]+)/);
                            if (driveMatch) {
                                imageUrl = 'https://lh3.googleusercontent.com/d/' + driveMatch[1];
                            }
                            const range = quill.getSelection();
                            quill.insertEmbed(range.index, 'image', imageUrl);
                        }
                    }
                }
            }
        }
    });

    const descriptionInput = document.getElementById('description-input');
    if (descriptionInput && descriptionInput.value) {
        quill.clipboard.dangerouslyPasteHTML(descriptionInput.value);
    }

    const form = document.getElementById('works-form');
    if (form) {
        form.addEventListener('submit', function() {
            descriptionInput.value = quill.root.innerHTML;
        });
    }
});