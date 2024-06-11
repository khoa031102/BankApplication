
    document
      .getElementById("signin-form")
      .addEventListener("submit", function (event) {
        event.preventDefault();

        var formData = new FormData(this);
        var jsonData = {};

        formData.forEach(function (value, key) {
          jsonData[key] = value;
        });

        fetch("/api/auth/sign-in", {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify(jsonData),
        })
          .then(function (response) {
            if (!response.ok) {
              throw new Error("Network response was not ok");
            }
            return response.json();
          })
          .then(function (data) {
            console.log(data);
            var token = data.token;
            if (token) {
              localStorage.setItem("token", token);
              window.location.href = "/home.html";
            } else {
              throw new Error("Token not found in response");
            }
          })
          .catch(function (error) {
            console.error(
              "There was a problem with your fetch operation:",
              error
            );
            alert("Đăng ký không thành công. Vui lòng thử lại sau.");
          });
      });
