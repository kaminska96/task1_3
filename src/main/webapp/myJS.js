// When the page loads, hide the result containers until they are needed
window.onload = function() { 
    var greetingResult = document.getElementById('greetingResult');
    var calculatorResult = document.getElementById('calculatorResult');
    var temperatureResult = document.getElementById('temperatureResult');

    // Hide the greeting result if it exists
    if (greetingResult) {
        greetingResult.style.display = 'none';
    }
    // Hide the calculator result if it exists
    if (calculatorResult) {
        calculatorResult.style.display = 'none';
    }
    // Hide the temperature result if it exists
    if (temperatureResult) {
        temperatureResult.style.display = 'none';
    }
};

// Function to send a greeting request to the server
function sendGreeting() {
    var name = document.getElementById("username").value; // Get the name from the input field
    var query = 'username=' + encodeURIComponent(name); // Prepare the query string
    doAjax('hello', query, 'handleGreetingResponse', 'get', 0); // Send the AJAX request
}

// Function to calculate numbers using the calculator servlet
function calculate() {
    var num1 = document.getElementById("num1").value; // Get the first number
    var num2 = document.getElementById("num2").value; // Get the second number
    var operation = document.getElementById("operation").value; // Get the selected operation
    var query = 'num1=' + encodeURIComponent(num1) + '&num2=' + encodeURIComponent(num2) + '&operation=' + encodeURIComponent(operation); // Prepare the query string
    doAjax('calculator', query, 'handleCalculatorResponse', 'get', 0); // Send the AJAX request
}

// Function to convert temperatures using the temperature converter servlet
function convertTemperature() {
    var tempValue = document.getElementById("tempValue").value; // Get the temperature value
    var conversionType = document.getElementById("conversionType").value; // Get the conversion type (CtoF or FtoC)
    var query = 'tempValue=' + encodeURIComponent(tempValue) + '&conversionType=' + encodeURIComponent(conversionType); // Prepare the query string
    doAjax('tempconverter', query, 'handleTemperatureResponse', 'get', 0); // Send the AJAX request
}

// Function to handle the response from the greeting servlet
function handleGreetingResponse(response) {
    var greetingResult = document.getElementById('greetingResult');
    if (greetingResult) {
        greetingResult.style.display = 'block'; // Show the result div
        greetingResult.innerHTML = response; // Display the response inside the result div
    }
}

// Function to handle the response from the calculator servlet
function handleCalculatorResponse(response) {
    var calculatorResult = document.getElementById('calculatorResult');
    if (calculatorResult) {
        calculatorResult.style.display = 'block'; // Show the result div
        calculatorResult.innerHTML = response; // Display the response inside the result div
    }
}

// Function to handle the response from the temperature converter servlet
function handleTemperatureResponse(response) {
    var temperatureResult = document.getElementById('temperatureResult');
    if (temperatureResult) {
        temperatureResult.style.display = 'block'; // Show the result div
        temperatureResult.innerHTML = `Result of conversion: ${response}`; // Display the response inside the result div
    }
}
