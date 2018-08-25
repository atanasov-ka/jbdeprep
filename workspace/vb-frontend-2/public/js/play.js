loadPlay = function () {
    data.box = JSON.parse(document.getElementById('boxJsonPayloadHolder').innerText.replace(/&quot;/g, '"'));
    data.indexes = JSON.parse(document.getElementById('playIndexPayloadHolder').innerText.replace(/&quot;/g, '"'));
    data.progressPercentStep = 100.0 / data.indexes.length;
    document.getElementById('showVerbHolder').classList.add("is-collapsed");
    document.getElementById('correctWrongHolder').classList.add("is-collapsed");

    var elements = document.getElementsByClassName("boxLangFront");
    Array.prototype.map.call(elements, e => e.innerText = data.box.box.front);

    elements = document.getElementsByClassName("boxLangBack");
    Array.prototype.map.call(elements, e => e.innerText = data.box.box.back);
};

getClassPerCorrectness = function (correctCount) {
    if (correctCount >= 3) {
        return "underline3p"
    } else if (correctCount === 2) {
        return "underline2p"
    } else if (correctCount === 1) {
        return "underline1p"
    } else if (correctCount === 0) {
        return "underline0"
    } else if (correctCount === -1) {
        return "underline1n"
    } else if (correctCount === -2) {
        return "underline2n"
    } else if (correctCount <= -3) {
        return "underline3n"
    }
};

loadNextVerb = function () {
    updateProgress();
    data.currentIndex++;
    if (data.indexes.length <= data.currentIndex) {
        alert("Box completed!");
        saveProgress();
        this.startPlay();
    } else {
        data.indexToLoad = data.indexes[data.currentIndex].i;
        data.currentSide = data.indexes[data.currentIndex].s;
        console.info("indexTOLoad: " + data.indexToLoad);
        document.getElementById('sideA').removeAttribute("class");
        if (data.currentSide === "b") {
            console.info("Side B");
            document.getElementById('sideA').classList.add(getClassPerCorrectness(data.box.verbPlayList[data.indexToLoad].correctBacks));
            document.getElementById('sideA').innerText = data.box.verbPlayList[data.indexToLoad].verb.front;
            document.getElementById('sideATranscription').innerText = data.box.verbPlayList[data.indexToLoad].verb.frontTranscription;
        } else {
            console.info("Side A");
            document.getElementById('sideA').classList.add(getClassPerCorrectness(data.box.verbPlayList[data.indexToLoad].correctFronts));
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

startPlay = function () {
    console.info("Start play...");
    data.currentIndex = -1;
    loadNextVerb();
};

listenSideA = function () {
    if (data.currentSide === "b") {
        let flush = new Audio(data.box.verbPlayList[data.indexToLoad].verb.frontAudio);
        flush.play();
    } else {
        let flush = new Audio(data.box.verbPlayList[data.indexToLoad].verb.backAudio);
        flush.play();
    }
};

listenSideB = function () {
    if (data.currentSide === "b") {
        let flush = new Audio(data.box.verbPlayList[data.indexToLoad].verb.backAudio);
        flush.play();
    } else {
        let flush = new Audio(data.box.verbPlayList[data.indexToLoad].verb.frontAudio);
        flush.play();
    }
};

showVerb = function () {
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

modifyCorrectnessHelper = function (current, step) {
    current += step;
    const MAX = 3, MIN = -3;
    if (step > 0 && current > MAX) {
        return MAX;
    } else if (step < 0 && current < MIN) {
        return MIN;
    } else {
        return current;
    }
};

modifyCorrectness = function (indexToLoad, step, side) {
    if (side === "b") {
        data.box.verbPlayList[indexToLoad].correctBacks = modifyCorrectnessHelper(data.box.verbPlayList[indexToLoad].correctBacks, step);
    } else {
        data.box.verbPlayList[indexToLoad].correctFronts = modifyCorrectnessHelper(data.box.verbPlayList[indexToLoad].correctFronts, step);
    }
};

markedCorrect = function () {
    modifyCorrectness(data.indexToLoad, 1, data.currentSide);
    loadNextVerb();
};

markedWrong = function () {
    modifyCorrectness(data.indexToLoad, -1, data.currentSide);
    loadNextVerb();
};

gatherResults = function () {
    let results = [];
    for (let i = 0; i < data.box.verbPlayList.length; ++i) {
        results = results.concat({
            "id": data.box.verbPlayList[i].id,
            "correctFronts": data.box.verbPlayList[i].correctFronts,
            "correctBacks": data.box.verbPlayList[i].correctBacks
        });
    }
    return results;
};

uploadResults = function (results) {
    let xhttp = new XMLHttpRequest();
    let url = "/play/" + data.box.box.id + "/saveProgress";

    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            let response = xhttp.responseText;
            if ("OK" === response) {
                document.getElementById("lastSaved").innerText = "Last updated at: " + new Date().toLocaleString();
            } else {
                alert("OPS! Can't update progress for some reason!")
            }
        }
    };

    xhttp.open("POST", url, true);
    xhttp.setRequestHeader("Content-type", "application/json");
    xhttp.send(JSON.stringify(results));
};

saveProgress = function () {
    uploadResults(gatherResults());
};

updateProgress = function () {
    let correctLevelBackHigh = 0;
    let correctLevelBackMid = 0;
    let correctLevelBackLow = 0;
    let correctLevelFrontHigh = 0;
    let correctLevelFrontMid = 0;
    let correctLevelFrontLow = 0;
    let verbPlayListSize = data.box.verbPlayList.length;
    for (let i = 0; i < verbPlayListSize; ++i) {

        switch (data.box.verbPlayList[i].correctBacks) {
            case 3:
            case 2:
                correctLevelBackHigh++;
                break;
            case 1:
            case 0:
            case -1:
                correctLevelBackMid++;
                break;
            case -2:
            case -3:
                correctLevelBackLow++;
                break;
        }

        switch (data.box.verbPlayList[i].correctFronts) {
            case 3:
            case 2:
                correctLevelFrontHigh++;
                break;
            case 1:
            case 0:
            case -1:
                correctLevelFrontMid++;
                break;
            case -2:
            case -3:
                correctLevelFrontLow++;
                break;
        }
    }

    let levelBackHigh = Math.round(correctLevelBackHigh / verbPlayListSize * 100);
    let levelBackMid = Math.round(correctLevelBackMid / verbPlayListSize * 100);
    let levelBackLow = Math.round(correctLevelBackLow / verbPlayListSize * 100);

    let levelFrontHigh = Math.round(correctLevelFrontHigh / verbPlayListSize * 100);
    let levelFrontMid = Math.round(correctLevelFrontMid / verbPlayListSize * 100);
    let levelFrontLow = Math.round(correctLevelFrontLow / verbPlayListSize * 100);

    updateProgressBar("levelBackHigh", levelBackHigh);
    updateProgressBar("levelBackMid", levelBackMid);
    updateProgressBar("levelBackLow", levelBackLow);
    updateProgressBar("levelFrontHigh", levelFrontHigh);
    updateProgressBar("levelFrontMid", levelFrontMid);
    updateProgressBar("levelFrontLow", levelFrontLow);
};

updateProgressBar = function (id, percentage) {
    document.getElementById(id).setAttribute("aria-valuenow", percentage.toString());
    document.getElementById(id).setAttribute("style", "width: " + percentage.toString() + "%");
    $('#' + id).text(Math.round(percentage).toString() + "%");
};

let data = {
    progressPercentStep: null,
    currentIndex: null,
    box: null,
    indexes: null,
    indexToLoad: null,
    currentSide: null
};
