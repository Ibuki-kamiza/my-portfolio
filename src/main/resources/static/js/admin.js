// サイドバーのアクティブリンク自動設定
function initActiveLink() {
    const currentPath = window.location.pathname;
    document.querySelectorAll('.admin-sidebar-links a').forEach(link => {
        if (link.getAttribute('href') === currentPath) {
            link.classList.add('active');
        }
    });
}

// テーブルの削除確認
function initDeleteConfirm() {
    document.querySelectorAll('form[data-confirm]').forEach(form => {
        form.addEventListener('submit', e => {
            if (!confirm(form.dataset.confirm)) {
                e.preventDefault();
            }
        });
    });
}

// フォームの未保存変更警告
function initUnsavedWarning() {
    const forms = document.querySelectorAll('.admin-form form');
    forms.forEach(form => {
        let isDirty = false;
        form.addEventListener('input', () => { isDirty = true; });
        form.addEventListener('submit', () => { isDirty = false; });
        window.addEventListener('beforeunload', e => {
            if (isDirty) {
                e.preventDefault();
                e.returnValue = '';
            }
        });
    });
}

// パスコード入力の自動フォーカス・自動送信
function initPasscodeInput() {
    const passcodeInput = document.querySelector('.passcode-input');
    if (!passcodeInput) return;

    passcodeInput.addEventListener('input', () => {
        if (passcodeInput.value.length === 6) {
            passcodeInput.closest('form').submit();
        }
    });
}

// 初期化
document.addEventListener('DOMContentLoaded', () => {
    initActiveLink();
    initDeleteConfirm();
    initUnsavedWarning();
    initPasscodeInput();
});