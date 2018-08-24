loadPlay = function(){
    data.box = JSON.parse(document.getElementById('boxJsonPayloadHolder').innerText.replace(/&quot;/g,'"'));
    data.indexes = JSON.parse(document.getElementById('playIndexPayloadHolder').innerText.replace(/&quot;/g,'"'));
    data.progressPercentStep = 100.0 / data.indexes.length;
    document.getElementById('showVerbHolder').classList.add("is-collapsed");
    document.getElementById('correctWrongHolder').classList.add("is-collapsed");
};

getClassPerCorrectness = function(correctCount) {
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

loadNextVerb = function() {
    data.currentIndex++;
    if (data.indexes.length <= data.currentIndex) {
        alert("Box completed!");
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

markedCorrect = function() {
    if (data.currentSide === "b") {
        data.box.verbPlayList[data.indexToLoad].correctBacks++;
    } else {
        data.box.verbPlayList[data.indexToLoad].correctFronts++;
    }
    loadNextVerb();
};

markedWrong = function() {
    if (data.currentSide === "b") {
        data.box.verbPlayList[data.indexToLoad].correctBacks--;
    } else {
        data.box.verbPlayList[data.indexToLoad].correctFronts--;
    }
    loadNextVerb();
};

gatherResults = function() {
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

uploadResults = function(results) {
    let xhttp = new XMLHttpRequest();
    let url = "/play/" + data.box.box.id + "/saveProgress";

    xhttp.onreadystatechange = function() {
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

saveProgress = function() {
    uploadResults(gatherResults());
};


let data = {
    progressPercentStep: null,
    currentIndex: null,
    box: null,
    indexes: null,
    indexToLoad: null,
    currentSide: null
};
