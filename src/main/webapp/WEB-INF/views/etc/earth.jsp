<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <!DOCTYPE html>
        <html>

        <head>
            <title>Earth Page</title>
            <meta charset="UTF-8" />
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/reset-css@4.0.1/reset.min.css" />
            <!-- reset -->
            <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/reset-css@4.0.1/reset.min.css" />
            <link href="https://fonts.google.com/specimen/Roboto" rel="stylesheet" />
            <link rel="stylesheet" href="/assets/css/UserRegister.css" />
            <link rel="icon" href="/assets/img/workwave_logo.png" />
            <style>
                body {
                    margin: 0;
                }

                canvas {
                    display: block;
                }
            </style>
        </head>

        <body>
            <!-- ㄱㄱㄱ -->
            <script src="https://cdnjs.cloudflare.com/ajax/libs/three.js/r128/three.min.js"></script>
            <script src="https://cdn.jsdelivr.net/npm/three@0.128.0/examples/js/controls/OrbitControls.js"></script>
            <script>
                let scene, camera, renderer, earth, graticule, controls;

                function init() {
                    scene = new THREE.Scene();
                    camera = new THREE.PerspectiveCamera(75, window.innerWidth / window.innerHeight, 0.1, 1000);
                    renderer = new THREE.WebGLRenderer();
                    renderer.setSize(window.innerWidth, window.innerHeight);
                    document.body.appendChild(renderer.domElement);

                    camera.position.z = 2;

                    controls = new THREE.OrbitControls(camera, renderer.domElement);
                    controls.enableDamping = true;
                    controls.dampingFactor = 0.25;
                    controls.enableZoom = true;

                    createEarth();
                    createGraticule();
                    createText();
                    animate();

                    window.addEventListener('resize', onWindowResize, false);
                }

                function createEarth() {
                    const geometry = new THREE.SphereGeometry(1, 32, 32);
                    const texture = new THREE.TextureLoader().load('https://threejs.org/examples/textures/land_ocean_ice_cloud_2048.jpg');
                    const material = new THREE.MeshPhongMaterial({ map: texture });
                    earth = new THREE.Mesh(geometry, material);
                    scene.add(earth);

                    const ambientLight = new THREE.AmbientLight(0x404040);
                    scene.add(ambientLight);

                    const pointLight = new THREE.PointLight(0xffffff, 1);
                    pointLight.position.set(5, 3, 5);
                    scene.add(pointLight);
                }

                function createGraticule() {
                    const material = new THREE.LineBasicMaterial({ color: 0xaaaaaa, transparent: true, opacity: 0.3 });
                    const radius = 1.01;
                    const segments = 64;
                    const graticuleGeometry = new THREE.BufferGeometry();
                    const positions = [];

                    for (let i = 0; i <= 18; i++) {
                        const latitude = (i - 9) * 10 * Math.PI / 180;
                        for (let j = 0; j <= segments; j++) {
                            const longitude = j * 360 / segments * Math.PI / 180;
                            const x = radius * Math.cos(latitude) * Math.cos(longitude);
                            const y = radius * Math.sin(latitude);
                            const z = radius * Math.cos(latitude) * Math.sin(longitude);
                            positions.push(x, y, z);
                        }
                    }

                    for (let i = 0; i <= 36; i++) {
                        const longitude = i * 10 * Math.PI / 180;
                        for (let j = 0; j <= segments; j++) {
                            const latitude = j * 180 / segments * Math.PI / 180 - Math.PI / 2;
                            const x = radius * Math.cos(latitude) * Math.cos(longitude);
                            const y = radius * Math.sin(latitude);
                            const z = radius * Math.cos(latitude) * Math.sin(longitude);
                            positions.push(x, y, z);
                        }
                    }

                    graticuleGeometry.setAttribute('position', new THREE.Float32BufferAttribute(positions, 3));
                    graticule = new THREE.LineSegments(graticuleGeometry, material);
                    scene.add(graticule);
                }

                function createText() {
                    const loader = new THREE.FontLoader();
                    loader.load('https://threejs.org/examples/fonts/helvetiker_regular.typeface.json', function (font) {
                        const textGeometry = new THREE.TextGeometry('                   workwave', {
                            font: font,
                            size: 0.2,
                            height: 0.05,
                            curveSegments: 12,
                            bevelEnabled: false
                        });
                        const textMaterial = new THREE.MeshPhongMaterial({ color: 0xffffff });
                        const textMesh = new THREE.Mesh(textGeometry, textMaterial);
                        textMesh.position.set(-0.4, 0, 1.1);
                        textMesh.lookAt(camera.position);
                        scene.add(textMesh);
                    });
                }

                function animate() {
                    requestAnimationFrame(animate);
                    controls.update();
                    renderer.render(scene, camera);
                }

                function onWindowResize() {
                    camera.aspect = window.innerWidth / window.innerHeight;
                    camera.updateProjectionMatrix();
                    renderer.setSize(window.innerWidth, window.innerHeight);
                }

                init();
            </script>
        </body>

        </html>