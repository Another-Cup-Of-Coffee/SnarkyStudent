async function fetchData() {
    const selectedCourse = document.querySelector('select').value;
    
    // 1. Grab all your boxes
    const precalcBox = document.getElementById('precalc-box');
    const box1 = document.getElementById('calc-1-box');
    const box2 = document.getElementById('calc-2-box');
    const box3 = document.getElementById('calc-3-box');

    // 2. Hide everything (The Reset)
    if(precalcBox) precalcBox.classList.add('hidden');
    box1.classList.add('hidden');
    box2.classList.add('hidden');
    box3.classList.add('hidden');

    // 3. Match by Number, not Name
    if (selectedCourse.includes("1093")) {
        precalcBox.classList.remove('hidden');
    } else if (selectedCourse.includes("1214")) {
        box1.classList.remove('hidden');
    } else if (selectedCourse.includes("1224")) { // This fixes the Calc II bug!
        box2.classList.remove('hidden');
    } else if (selectedCourse.includes("2214")) {
        box3.classList.remove('hidden');
    }

    // 4. Update the Snarky Console
    try {
        const response = await fetch(`http://localhost:8080/api/data?course=${encodeURIComponent(selectedCourse)}`);
        const text = await response.text();
        document.getElementById('response').innerText = "> " + text;
    } catch (err) {
        document.getElementById('response').innerText = "> Error: Backend not found at port 8080.";
    }
}