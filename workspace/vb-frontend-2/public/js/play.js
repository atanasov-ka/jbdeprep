loadPlay = function(){
    data.box = JSON.parse(document.getElementById('boxJsonPayloadHolder').innerText.replace(/&quot;/g,'"'));
    data.progressPercentStep = 100.0 / data.box.verbPlayList.length;
    document.getElementById('showVerbHolder').classList.add("is-collapsed");
    document.getElementById('correctWrongHolder').classList.add("is-collapsed");
};

loadNextVerb = function() {
    data.currentIndex++;
    if (data.box.verbPlayList.length <= data.currentIndex) {
        alert("Box completed!");
        this.startPlay();
    } else {
        document.getElementById('sideA').innerText = data.box.verbPlayList[data.currentIndex].verb.front;
        document.getElementById('sideATranscription').innerText = data.box.verbPlayList[data.currentIndex].verb.frontTranscription;
        document.getElementById('sideB').innerText = "-";
        document.getElementById('sideBTranscription').innerText = "-";
    }

    document.getElementById('showVerbHolder').classList.remove("is-collapsed");
    document.getElementById('correctWrongHolder').classList.add("is-collapsed");
    let flush = new Audio(data.box.verbPlayList[data.currentIndex].verb.frontAudio);
    flush.play();
    let currentProgress = data.progressPercentStep * (data.currentIndex + 1);
    document.getElementById('currentBoxProgress').setAttribute("aria-valuenow", currentProgress.toString());
    document.getElementById('currentBoxProgress').setAttribute("style", "width: " + currentProgress.toString() + "%");
    $('#currentBoxProgress').text(Math.round(currentProgress).toString() + "%");
};

markedCorrect = function() {
    loadNextVerb()
};

markedWrong = function() {
    loadNextVerb()

};

startPlay = function() {
    console.info("Start play...");
    data.currentIndex = -1;
    loadNextVerb();
};

showVerb = function() {
    document.getElementById('sideB').innerText = data.box.verbPlayList[data.currentIndex].verb.back;
    document.getElementById('sideBTranscription').innerText = data.box.verbPlayList[data.currentIndex].verb.backTranscription;

    document.getElementById('showVerbHolder').classList.add("is-collapsed");
    document.getElementById('correctWrongHolder').classList.remove("is-collapsed");
};

let data = {
    progressPercentStep: null,
    currentIndex: null,
    box: null
};

