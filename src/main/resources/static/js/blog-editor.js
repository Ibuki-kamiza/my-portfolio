document.addEventListener('DOMContentLoaded', function() {
    const editorEl = document.getElementById('quill-blog-editor');
    if (!editorEl) return;

    const quill = new Quill('#quill-blog-editor', {
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

    const contentInput = document.getElementById('content-input');
    if (contentInput && contentInput.value) {
        quill.clipboard.dangerouslyPasteHTML(contentInput.value);
    }

    const form = document.getElementById('blog-form');
    if (form) {
        form.addEventListener('submit', function() {
            contentInput.value = quill.root.innerHTML;
        });
    }
});