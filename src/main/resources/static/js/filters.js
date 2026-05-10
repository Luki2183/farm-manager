document.addEventListener('DOMContentLoaded', function () {

    // ── Area range (min + max) ────────────────────────────────────────────────
    const minSlider   = document.getElementById('minArea');
    const maxSlider   = document.getElementById('maxArea');
    const rangeLabels = document.querySelectorAll('.slider-range-label span');

    function updateAreaLabels() {
        let min = parseInt(minSlider.value);
        let max = parseInt(maxSlider.value);

        // Prevent min from exceeding max and vice versa
        if (min > max) { minSlider.value = max; min = max; }
        if (max < min) { maxSlider.value = min; max = min; }

        if (rangeLabels.length === 2) {
            rangeLabels[0].textContent = min;
            rangeLabels[1].textContent = max;
        }
    }

    if (minSlider && maxSlider) {
        minSlider.addEventListener('input', updateAreaLabels);
        maxSlider.addEventListener('input', updateAreaLabels);
    }

    // ── Single sliders (humidity, wind) ──────────────────────────────────────
    document.querySelectorAll('.range-slider.single').forEach(function (slider) {
        const label = slider.closest('.field-group').querySelector('.slider-value-label');
        if (!label) return;

        const unit = slider.id === 'humidity' ? '%' : ' km/h';

        slider.addEventListener('input', function () {
            label.textContent = slider.value + unit;
        });
    });
});