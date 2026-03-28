// カスタムカーソル
function initCursor() {
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
}

// 背景の動く図形
function initBackgroundShapes() {
    const canvas = document.createElement('canvas');
    canvas.style.cssText = `
        position: fixed; top: 0; left: 0;
        width: 100%; height: 100%;
        pointer-events: none; z-index: 0; opacity: 0.4;
    `;
    document.body.insertBefore(canvas, document.body.firstChild);

    const ctx = canvas.getContext('2d');
    let mouseX = window.innerWidth / 2;
    let mouseY = window.innerHeight / 2;

    function resizeCanvas() {
        canvas.width = window.innerWidth;
        canvas.height = window.innerHeight;
    }
    resizeCanvas();
    window.addEventListener('resize', resizeCanvas);

    document.addEventListener('mousemove', e => {
        mouseX = e.clientX;
        mouseY = e.clientY;
    });

    const shapes = Array.from({ length: 6 }, (_, i) => ({
        x: Math.random() * window.innerWidth,
        y: Math.random() * window.innerHeight,
        size: 80 + Math.random() * 120,
        speedX: (Math.random() - 0.5) * 0.4,
        speedY: (Math.random() - 0.5) * 0.4,
        opacity: 0.03 + Math.random() * 0.05,
        type: i % 3
    }));

    function animate() {
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
                ctx.rect(shape.x - shape.size / 2, shape.y - shape.size / 2, shape.size, shape.size);
            } else {
                ctx.moveTo(shape.x, shape.y - shape.size);
                ctx.lineTo(shape.x + shape.size, shape.y + shape.size);
                ctx.lineTo(shape.x - shape.size, shape.y + shape.size);
            }
            ctx.fill();
        });
        requestAnimationFrame(animate);
    }
    animate();
}

// 初期化
document.addEventListener('DOMContentLoaded', () => {
    initCursor();
    initBackgroundShapes();
});