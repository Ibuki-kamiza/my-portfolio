// カスタムカーソル
const cursor = document.createElement('div');
const cursorDot = document.createElement('div');
cursor.style.cssText = `
    position: fixed; width: 40px; height: 40px;
    border: 1px solid rgba(139,115,85,0.5); border-radius: 50%;
    pointer-events: none; z-index: 9999;
    transition: transform 0.15s ease, opacity 0.3s;
    transform: translate(-50%, -50%);
`;
cursorDot.style.cssText = `
    position: fixed; width: 6px; height: 6px;
    background: #8b7355; border-radius: 50%;
    pointer-events: none; z-index: 9999;
    transform: translate(-50%, -50%);
`;
document.body.appendChild(cursor);
document.body.appendChild(cursorDot);

document.addEventListener('mousemove', e => {
    cursor.style.left = e.clientX + 'px';
    cursor.style.top = e.clientY + 'px';
    cursorDot.style.left = e.clientX + 'px';
    cursorDot.style.top = e.clientY + 'px';
});

document.addEventListener('mousedown', () => {
    cursor.style.transform = 'translate(-50%, -50%) scale(0.7)';
});
document.addEventListener('mouseup', () => {
    cursor.style.transform = 'translate(-50%, -50%) scale(1)';
});

// 背景の動く図形
const canvas = document.createElement('canvas');
canvas.style.cssText = `
    position: fixed; top: 0; left: 0;
    width: 100%; height: 100%;
    pointer-events: none; z-index: 0; opacity: 0.4;
`;
document.body.insertBefore(canvas, document.body.firstChild);

const ctx = canvas.getContext('2d');
let mouseX = 0, mouseY = 0;

window.addEventListener('resize', resizeCanvas);
document.addEventListener('mousemove', e => {
    mouseX = e.clientX;
    mouseY = e.clientY;
});

function resizeCanvas() {
    canvas.width = window.innerWidth;
    canvas.height = window.innerHeight;
}
resizeCanvas();

const shapes = Array.from({length: 6}, (_, i) => ({
    x: Math.random() * window.innerWidth,
    y: Math.random() * window.innerHeight,
    size: 80 + Math.random() * 120,
    speedX: (Math.random() - 0.5) * 0.4,
    speedY: (Math.random() - 0.5) * 0.4,
    opacity: 0.03 + Math.random() * 0.05,
    type: i % 3
}));

function animateShapes() {
    ctx.clearRect(0, 0, canvas.width, canvas.height);
    shapes.forEach(shape => {
        shape.x += shape.speedX + (mouseX - window.innerWidth / 2) * 0.00015;
        shape.y += shape.speedY + (mouseY - window.innerHeight / 2) * 0.00015;
        if (shape.x < -200) shape.x = canvas.width + 200;
        if (shape.x > canvas.width + 200) shape.x = -200;
        if (shape.y < -200) shape.y = canvas.height + 200;
        if (shape.y > canvas.height + 200) shape.y = -200;

        ctx.beginPath();
        ctx.fillStyle = `rgba(139, 115, 85, ${shape.opacity})`;
        if (shape.type === 0) {
            ctx.arc(shape.x, shape.y, shape.size, 0, Math.PI * 2);
        } else if (shape.type === 1) {
            ctx.rect(shape.x - shape.size/2, shape.y - shape.size/2, shape.size, shape.size);
        } else {
            ctx.moveTo(shape.x, shape.y - shape.size);
            ctx.lineTo(shape.x + shape.size, shape.y + shape.size);
            ctx.lineTo(shape.x - shape.size, shape.y + shape.size);
        }
        ctx.fill();
    });
    requestAnimationFrame(animateShapes);
}
animateShapes();

// スクロールアニメーション
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

// スキル円グラフアニメーション
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

// ナビのスクロール効果
window.addEventListener('scroll', () => {
    const nav = document.querySelector('.nav');
    if (window.scrollY > 50) {
        nav.style.boxShadow = '0 2px 20px rgba(0,0,0,0.05)';
    } else {
        nav.style.boxShadow = 'none';
    }
});