 function getCurrentAccount() {
        var token = localStorage.getItem('token');

        if (!token) {
            console.error('No authentication token found in localStorage');
            return;
        }

        fetch('/api/user/account', {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + token
            }
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then(data => {
                const cardNumber = data.cardNumber;

                const divElement = document.getElementById("account-info");

                divElement.textContent = cardNumber;
            })
            .catch(error => {
                console.error('Error fetching current account:', error);
            });
    }

    getCurrentAccount();

    document.getElementById('transfer-form').addEventListener('submit', function (event) {
            event.preventDefault();
            var token = localStorage.getItem('token');

            if (!token) {
                console.error('No authentication token found in localStorage');
                return;
            }

            var formData = new FormData(this);
            var jsonData = {};

            formData.forEach(function (value, key) {
                jsonData[key] = value;
            });

            fetch('/api/user/transfer', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': 'Bearer ' + token
                },
                body: JSON.stringify(jsonData)
            })
                .then(function (response) {
                    if (!response.ok) {
                        throw new Error('Network response was not ok');
                    }
                    return response.json();
                })
                .then(function (data) {
                    console.log(data);
                    alert('Giao dịch thành công!');
                    window.location.href = "/home.html";
                })
                .catch(function (error) {
                    console.error('There was a problem with your fetch operation:', error);
                    alert('Đăng ký không thành công. Vui lòng thử lại sau.');
                });
        });