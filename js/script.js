//    ->  hero Selection  <-

document.addEventListener("DOMContentLoaded", function () {
  const slideshow = document.getElementById('background-slideshow');
  if (!slideshow) return; // Ensure element exists

  const images = [
    "url('./image/a.jpeg')",
    "url('./image/b.jpg')",
    "url('./image/c.jpeg')"
  ];

  let currentIndex = 0;

  function changeBackground() {
    slideshow.style.transition = 'opacity 0.4s'; // Add smooth transition if not set in CSS
    slideshow.style.opacity = '0';
    setTimeout(() => {
      slideshow.style.backgroundImage = images[currentIndex];
      currentIndex = (currentIndex + 1) % images.length;
      slideshow.style.opacity = '1';
    }, 400);
  }

  changeBackground(); // initial
  setInterval(changeBackground, 5000); // change every 5s
});

// payment
function redirectToPayment() {
  window.location.href = 'section/payment-page.html';
}


//sign up option


document.addEventListener('DOMContentLoaded', function () {
  // ১. সবগুলো ইনপুট ফিল্ড এবং বাটনকে সিলেক্ট করুন
  const nameInput = document.getElementById('name');
  const emailInput = document.getElementById('email');
  const phoneInput = document.getElementById('phone');
  const passwordInput = document.getElementById('password');
  const confirmPasswordInput = document.getElementById('confirm-password');
  const signupLink = document.getElementById('signup-link');
  const passwordError = document.getElementById('password-error');

  // বাটনের আসল লিংক একটি ভেরিয়েবলে রাখুন
  const originalHref = signupLink.href;

  // ২. ইনপুট ফিল্ডগুলো চেক করার জন্য একটি ফাংশন তৈরি করুন
  function validateForm() {
    // সবগুলো ফিল্ড পূরণ করা হয়েছে কিনা তা চেক করুন
    const allFieldsFilled = nameInput.value.trim() !== '' &&
      emailInput.value.trim() !== '' &&
      phoneInput.value.trim() !== '' &&
      passwordInput.value.trim() !== '' &&
      confirmPasswordInput.value.trim() !== '';

    // পাসওয়ার্ড দুটি মিলছে কিনা তা চেক করুন
    const passwordsMatch = passwordInput.value === confirmPasswordInput.value;

    // পাসওয়ার্ড না মিললে মেসেজ দেখান
    if (passwordInput.value && confirmPasswordInput.value && !passwordsMatch) {
      passwordError.textContent = 'Passwords do not match.';
    } else {
      passwordError.textContent = ''; // মিললে মেসেজ মুছে ফেলুন
    }

    // ৩. যদি সবগুলো ফিল্ড পূরণ থাকে এবং পাসওয়ার্ড মিলে যায়, বাটনটি চালু করুন
    if (allFieldsFilled && passwordsMatch) {
      signupLink.classList.remove('opacity-50', 'pointer-events-none');
      signupLink.href = originalHref; // লিংকটি আবার চালু করুন
    } else {
      // অন্যথায়, বাটনটি ডিজেবল রাখুন
      signupLink.classList.add('opacity-50', 'pointer-events-none');
      signupLink.removeAttribute('href'); // লিংকটি বন্ধ করুন
    }
  }

  // ৪. প্রতিটি ইনপুট ফিল্ডে টাইপ করার সাথে সাথে validateForm() ফাংশনটি চালান
  const inputs = [nameInput, emailInput, phoneInput, passwordInput, confirmPasswordInput];
  inputs.forEach(input => {
    input.addEventListener('input', validateForm);
  });

  // ৫. পেইজ লোড হওয়ার সময় একবার চালিয়ে বাটনটি ডিজেবল করে রাখুন
  validateForm();
});




// for confirm payment


document.addEventListener('DOMContentLoaded', function () {
  const providerSelect = document.getElementById('provider-select');
  const instructionsDiv = document.getElementById('mobile-banking-instructions');
  const providerError = document.getElementById('provider-error');
  const confirmBtn = document.getElementById('confirm-btn');

  const instructions = {
    bKash: `
          <h6 class="font-semibold text-gray-800 mb-2">bKash Payment Instructions</h6>
          <ol class="list-decimal list-inside space-y-1">
            <li>Go to your bKash App or dial <strong>*247#</strong>.</li>
            <li>Choose 'Payment'.</li>
            <li>Enter our merchant number: <strong>01XXXXXXXXX</strong></li>
            <li>Enter amount: <strong>৳50.00</strong></li>
            <li>Enter reference: <strong>APP-123456789</strong></li>
            <li>Enter your PIN to confirm.</li>
            <li>After payment, enter the Transaction ID (TrxID) below.</li>
          </ol>
          <div class="mt-4">
            <label for="trxId" class="block text-sm font-medium text-gray-700">Transaction ID (TrxID)</label>
            <input type="text" id="trxId" class="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm" placeholder="e.g., 9G7H3F2J1K">
            <p id="trx-error" class="text-red-500 text-sm mt-1 hidden">Please enter your Transaction ID.</p>
          </div>
        `,
    Nagad: `
          <h6 class="font-semibold text-gray-800 mb-2">Nagad Payment Instructions</h6>
          <ol class="list-decimal list-inside space-y-1">
            <li>Go to your Nagad App or dial <strong>*167#</strong>.</li>
            <li>Choose 'Payment'.</li>
            <li>Enter our merchant number: <strong>01XXXXXXXXX</strong></li>
            <li>Enter amount: <strong>৳50.00</strong></li>
            <li>Enter reference: <strong>APP-123456789</strong></li>
            <li>Enter your PIN to confirm.</li>
            <li>After payment, enter the Transaction ID (TrxID) below.</li>
          </ol>
          <div class="mt-4">
            <label for="trxId" class="block text-sm font-medium text-gray-700">Transaction ID (TrxID)</label>
            <input type="text" id="trxId" class="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm" placeholder="e.g., 9G7H3F2J1K">
            <p id="trx-error" class="text-red-500 text-sm mt-1 hidden">Please enter your Transaction ID.</p>
          </div>
        `,
    Rocket: `
          <h6 class="font-semibold text-gray-800 mb-2">Rocket Payment Instructions</h6>
          <ol class="list-decimal list-inside space-y-1">
            <li>Go to your Rocket App or dial <strong>*322#</strong>.</li>
            <li>Choose 'Payment'.</li>
            <li>Enter our merchant number: <strong>01XXXXXXXXX</strong></li>
            <li>Enter amount: <strong>৳50.00</strong></li>
            <li>Enter reference: <strong>APP-123456789</strong></li>
            <li>Enter your PIN to confirm.</li>
            <li>After payment, enter the Transaction ID (TrxID) below.</li>
          </ol>
          <div class="mt-4">
            <label for="trxId" class="block text-sm font-medium text-gray-700">Transaction ID (TrxID)</label>
            <input type="text" id="trxId" class="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm" placeholder="e.g., 9G7H3F2J1K">
            <p id="trx-error" class="text-red-500 text-sm mt-1 hidden">Please enter your Transaction ID.</p>
          </div>
        `
  };

  providerSelect.addEventListener('change', function () {
    const selectedProvider = this.value;
    if (selectedProvider && instructions[selectedProvider]) {
      instructionsDiv.innerHTML = instructions[selectedProvider];
      instructionsDiv.classList.remove('hidden');
      providerError.classList.add('hidden');
    } else {
      instructionsDiv.classList.add('hidden');
      instructionsDiv.innerHTML = '';
    }
  });

  // Validation on Confirm
  confirmBtn.addEventListener('click', function () {
    let valid = true;
    const provider = providerSelect.value;
    const trxIdInput = document.querySelector('#mobile-banking-instructions #trxId');
    const trxError = document.querySelector('#mobile-banking-instructions #trx-error');

    // Check provider
    if (!provider) {
      providerError.classList.remove('hidden');
      valid = false;
    } else {
      providerError.classList.add('hidden');
    }

    // Check TrxID
    if (!trxIdInput || trxIdInput.value.trim() === '') {
      trxError.classList.remove('hidden');
      valid = false;
    } else {
      trxError.classList.add('hidden');
    }

    // If all good → redirect
    if (valid) {
      window.location.href = "./userdashboard.html";
    }
  });
});

// Particle.js initialization
document.addEventListener('DOMContentLoaded', function () {
  if (document.getElementById('particles-js')) {
    particlesJS('particles-js', {
      "particles": {
        "number": {
          "value": 80,
          "density": {
            "enable": true,
            "value_area": 800
          }
        },
        "color": {
          "value": "#ffffff"
        },
        "shape": {
          "type": "circle",
          "stroke": {
            "width": 0,
            "color": "#000000"
          },
          "polygon": {
            "nb_sides": 5
          }
        },
        "opacity": {
          "value": 0.5,
          "random": false,
          "anim": {
            "enable": false,
            "speed": 1,
            "opacity_min": 0.1,
            "sync": false
          }
        },
        "size": {
          "value": 3,
          "random": true,
          "anim": {
            "enable": false,
            "speed": 40,
            "size_min": 0.1,
            "sync": false
          }
        },
        "line_linked": {
          "enable": true,
          "distance": 150,
          "color": "#ffffff",
          "opacity": 0.4,
          "width": 1
        },
        "move": {
          "enable": true,
          "speed": 6,
          "direction": "none",
          "random": false,
          "straight": false,
          "out_mode": "out",
          "bounce": false,
          "attract": {
            "enable": false,
            "rotateX": 600,
            "rotateY": 1200
          }
        }
      },
      "interactivity": {
        "detect_on": "canvas",
        "events": {
          "onhover": {
            "enable": true,
            "mode": "repulse"
          },
          "onclick": {
            "enable": true,
            "mode": "push"
          },
          "resize": true
        },
        "modes": {
          "grab": {
            "distance": 400,
            "line_linked": {
              "opacity": 1
            }
          },
          "bubble": {
            "distance": 400,
            "size": 40,
            "duration": 2,
            "opacity": 8,
            "speed": 3
          },
          "repulse": {
            "distance": 200,
            "duration": 0.4
          },
          "push": {
            "particles_nb": 4
          },
          "remove": {
            "particles_nb": 2
          }
        }
      },
      "retina_detect": true
    });
  }
});



