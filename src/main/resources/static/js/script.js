async function fetchData() {
    const selectedCourse = document.querySelector('select').value;
    
    const precalcBox = document.getElementById('precalc-box');
    const box1 = document.getElementById('calc-1-box');
    const responseConsole = document.getElementById('response');

    // 1. Hide everything (The Reset)
    precalcBox.classList.add('hidden');
    box1.classList.add('hidden');

    // 2. Show based on Course Number
    if (selectedCourse.includes("1093")) {
        precalcBox.classList.remove('hidden');
    } else if (selectedCourse.includes("1214")) {
        box1.classList.remove('hidden');
    }

    // 3. Update the Snarky Console from Java
    try {
        const response = await fetch(`http://localhost:8080/api/data?course=${encodeURIComponent(selectedCourse)}`);
        const text = await response.text();
        responseConsole.innerText = "> " + text;
    } catch (err) {
        responseConsole.innerText = "> Error: Backend not found at port 8080.";
    }
}