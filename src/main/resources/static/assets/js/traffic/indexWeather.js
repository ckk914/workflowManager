
let cityName = '';
let temperature = '';
let currentWeather = '';
let currentHumidity = '';
let windSpeed ='';

let indexWeatherInfo = '';


 function getWeather() {
    const apiKey = "a54f739fb74fe16024127bca11322fe5";
    const city = "Seoul";
    const urlString = `https://api.openweathermap.org/data/2.5/weather?q=${city}&appid=${apiKey}`;

    fetch(urlString)
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok ' + response.statusText);
            }
            return response.json();
        })
        .then(data => {
            const cityName = data.name;
            const temp = (data.main.temp - 273.15).toFixed(2) + '°C';
            const weather = data.weather[0].main;
            const humidity = data.main.humidity + '%';
            const windSpeed = data.wind.speed + ' m/s';

            const weatherInfo = `
                <div class="weather-info">
                    <strong><i class="fas fa-map-marked-alt"></i></strong> ${cityName} 
                    <strong><i class="fas fa-temperature-low"></i></strong> ${temp} 
                    <strong><i class="fas fa-cloud-sun-rain"></i></strong> ${weather} 
                    <strong><i class="fas fa-tint"></i></strong> ${humidity} 
                    <strong><i class="fas fa-wind"></i></strong> ${windSpeed}
                </div>     
            `;

            document.getElementById("index-weather").innerHTML = weatherInfo;
        
        })
        .catch(error => {
            document.getElementById("index-weather").innerHTML = `Error: ${error}`;
        });
}


getWeather();