loadPlay = function(){
    data.box = JSON.parse(document.getElementById('boxJsonPayloadHolder').innerText.replace(/&quot;/g,'"'));
    data.indexes = JSON.parse(document.getElementById('playIndexPayloadHolder').innerText.replace(/&quot;/g,'"'));
    data.progressPercentStep = 100.0 / data.indexes.length;
    document.getElementById('showVerbHolder').classList.add("is-collapsed");
    document.getElementById('correctWrongHolder').classList.add("is-collapsed");
};

loadNextVerb = function() {
    data.currentIndex++;
    if (data.indexes.length <= data.currentIndex) {
        alert("Box completed!");
        this.startPlay();
    } else {
        data.indexToLoad = data.indexes[data.currentIndex].i;
        data.currentSide = data.indexes[data.currentIndex].s;
        console.info("indexTOLoad: " + data.indexToLoad);
        if (data.currentSide === "b") {
            console.info("Side B");
            document.getElementById('sideA').innerText = data.box.verbPlayList[data.indexToLoad].verb.front;
            document.getElementById('sideATranscription').innerText = data.box.verbPlayList[data.indexToLoad].verb.frontTranscription;
        } else {
            console.info("Side A");
            document.getElementById('sideA').innerText = data.box.verbPlayList[data.indexToLoad].verb.back;
            document.getElementById('sideATranscription').innerText = data.box.verbPlayList[data.indexToLoad].verb.backTranscription;
        }

        document.getElementById('sideB').innerText = "-";
        document.getElementById('sideBTranscription').innerText = "-";
    }

    document.getElementById('showVerbHolder').classList.remove("is-collapsed");
    document.getElementById('correctWrongHolder').classList.add("is-collapsed");
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

listenSideA = function() {
    if (data.currentSide === "b") {
        let flush = new Audio(data.box.verbPlayList[data.indexToLoad].verb.frontAudio);
        flush.play();
    } else {
        let flush = new Audio(data.box.verbPlayList[data.indexToLoad].verb.backAudio);
        flush.play();
    }
};

listenSideB = function() {
    if (data.currentSide === "b") {
        let flush = new Audio(data.box.verbPlayList[data.indexToLoad].verb.backAudio);
        flush.play();
    } else {
        let flush = new Audio(data.box.verbPlayList[data.indexToLoad].verb.frontAudio);
        flush.play();
    }
};

showVerb = function() {
    if (data.currentSide === "b") {
        console.info("Side B");
        document.getElementById('sideB').innerText = data.box.verbPlayList[data.indexToLoad].verb.back;
        document.getElementById('sideBTranscription').innerText = data.box.verbPlayList[data.indexToLoad].verb.backTranscription;
    } else {
        console.info("Side A");
        document.getElementById('sideB').innerText = data.box.verbPlayList[data.indexToLoad].verb.front;
        document.getElementById('sideBTranscription').innerText = data.box.verbPlayList[data.indexToLoad].verb.frontTranscription;
    }

    document.getElementById('showVerbHolder').classList.add("is-collapsed");
    document.getElementById('correctWrongHolder').classList.remove("is-collapsed");
};

let data = {
    progressPercentStep: null,
    currentIndex: null,
    box: null,
    indexes: null,
    indexToLoad: null,
    currentSide: null
};

