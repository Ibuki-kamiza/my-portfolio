// スクロールフェードインアニメーション
function initScrollAnimations() {
    const observer = new IntersectionObserver((entries) => {
        entries.forEach(entry => {
            if (entry.isIntersecting) {
                entry.target.style.opacity = '1';
                entry.target.style.transform = 'translateY(0)';
            }
        });
    }, { threshold: 0.1 });

    document.querySelectorAll('.skill-card, .work-card, .blog-card').forEach(el => {
        el.style.opacity = '0';
        el.style.transform = 'translateY(30px)';
        el.style.transition = 'opacity 0.6s ease, transform 0.6s ease';
        observer.observe(el);
    });
}

// スキル円グラフアニメーション
function initSkillCircles() {
    document.querySelectorAll('.skill-progress').forEach(circle => {
        const level = parseInt(circle.dataset.level) || 50;
        const circumference = 2 * Math.PI * 28;
        const offset = circumference - (level / 100) * circumference;
        circle.style.strokeDasharray = circumference;
        circle.style.strokeDashoffset = circumference;
        setTimeout(() => {
            circle.style.transition = 'stroke-dashoffset 1.5s ease';
            circle.style.strokeDashoffset = offset;
        }, 300);
    });
}

// ナビスクロール効果
function initNavScroll() {
    const nav = document.querySelector('.nav');
    if (!nav) return;
    window.addEventListener('scroll', () => {
        if (window.scrollY > 50) {
            nav.style.boxShadow = '0 2px 20px rgba(0,0,0,0.05)';
        } else {
            nav.style.boxShadow = 'none';
        }
    });
}

// タイムラインアニメーション
function initTimeline() {
    const timelineItems = document.querySelectorAll('.timeline-item');
    if (timelineItems.length === 0) return;

    const observer = new IntersectionObserver((entries) => {
        entries.forEach((entry, index) => {
            if (entry.isIntersecting) {
                setTimeout(() => {
                    entry.target.classList.add('visible');
                }, index * 150);
            }
        });
    }, { threshold: 0.2 });

    timelineItems.forEach(item => observer.observe(item));
}

// 初期化
document.addEventListener('DOMContentLoaded', () => {
    initScrollAnimations();
    initSkillCircles();
    initNavScroll();
    initTimeline();
});