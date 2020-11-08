import { LitElement, html, css } from 'lit-element';

import { sessionEstablish, removeSession, read } from './storage.js';

export class ingHeader extends LitElement {
  static get properties() {
    return {
      logindisplay: { type: Boolean },
      message: { type: String },
      pos: { type: Boolean },
    };
  }

  static get styles() {
    return css`
      :host {
        display: flex;
        flex-direction: column;
        padding: 0.6rem;
        color: #c3c3c3;
      }
      #firstpage {
        background-image: url('./src/bg.jpg');
        width: 100%;
        min-height: 650px;
        font-size: 1.3rem;
        color: #7400b8;
        position: relative;
        padding: 0px 50px 50px 50px;
        font-family: Courier;
      }

      .error {
        color: #ff6961;
        position: absolute;
        bottom: 0px;
        left: 50px;
      }

      .success {
        color: green;
        position: absolute;
        bottom: 0px;
        left: 50px;
      }

      h1 {
        color: #6930c3;
        margin-top: 10%;
        font-size: 3rem;
        font-family: Courier;
      }

      #signupbutton {
        border-radius: 25px;
        padding: 10px 30px 10px 30px;
        display: block;
        margin-left: auto;
        margin-right: auto;
        margin-top: 20%;
        font-size: 1.5rem;
        width: 350px;
        transition: 1s all;
        color: #7400b8;
        background-color: Transparent;
        font-family: Courier;
      }

      #loginbutton {
        border-radius: 25px;
        padding: 10px 30px 10px 30px;
        display: block;
        margin-left: auto;
        margin-right: auto;
        margin-top: 50px;
        font-size: 1.5rem;
        width: 350px;
        transition: 1s all;
        background-color: Transparent;
        color: #7400b8;
        font-family: Courier;
      }

      #signupbutton:hover {
        border: 2px solid blue;
        background-color: #80ffdb;
      }

      #loginbutton:hover {
        border: 2px solid blue;
        background-color: #80ffdb;
      }

      #loginform {
        margin-top: 20%;
        color: #7400b8;
        font-family: Courier;
        font-weight: 900;
      }

      #loginform .formbutton {
        width: 300px;
        padding: 10px 30px 10px 30px;
        margin-top: 5%;
        background-color: Transparent;
        transition: 1s all;
        color: #7400b8;
        font-family: Courier;
        font-size: 1.5rem;
      }

      #loginform .formbutton:hover {
        background-color: #80ffdb;
        border: 2px solid blue;
      }

      #accemail {
        margin-top: 20px;
        width: 350px;
        padding: 10px 30px 10px 30px;
      }

      #accpassword {
        margin-top: 20px;
        width: 350px;
        padding: 10px 30px 10px 30px;
      }

      #nextRegister {
        display: block;
        float: right;
        width: 60px;
        border-radius: 30px;
        border: 1px solid black;
        position: absolute;
        bottom: 10px;
        right: 10px;
        transition: 1s all;
      }

      #nextRegister:hover {
        border: 1px solid black;
        filter: contrast(200%);
      }

      .topic img {
        margin-top: 10px;
        width: 200px;
        display: block;
        margin-left: auto;
        margin-right: auto;
      }

      .topic {
        border: 1px solid black;
        background-color: rgba(94, 96, 206, 0.2);
        display: inline-block;
        margin: 25px 25px 0px 25px;
        width: 200px;
      }

      #registerForm2 {
        display: none;
      }

      #firstpage .reg2 {
        margin-top: 20px;
        width: 200px;
        padding: 10px 30px 10px 30px;
        display: block;
        margin-left: auto;
        margin-right: auto;
        margin-top: 10px;
      }

      #firstpage .reg3 {
        border: 1px;
      }

      #firstpage .formEntry {
        width: 300px;
        display: inline-block;
        margin-bottom: 25px;
        margin-top: 25px;
      }

      #firstpage .formEntry label {
        float: left;
      }
      #firstpage .formEntry input {
        align: center;
        border: 2px solid #7400b8;
      }

      #firstpage .img3 {
        height: 100px;
      }

      #firstpage .optionable {
        border: 1px solid black;
        background-color: rgba(94, 96, 206, 0.2);
        width: 200px;
        display: inline-block;
        margin-left: auto;
        margin-right: auto;
      }

      #firstpage .optionable:hover {
        background-color: rgba(94, 96, 206, 0.6);
      }

      #profile {
        width: 50%;
        margin-left: auto;
        margin-right: auto;
        margin-top: 5%;
        margin-bottom: 10%;
      }

      .user {
        background-color: #48bfe3;
        border: 1px solid black;
        margin-left: auto;
        margin-right: auto;
        padding: 5px 40px 10px 40px;
        margin-top: 30px;
        border-radius: 15px;
        min-width: 100px;
        color: #7400b8;
      }

      #nearby {
        float: left;
        margin-left: 50px;
        padding: 10px 15px 10px 15px;
        color: black;
        width: 250px;
      }

      #similar {
        float: right;
        margin-right: 50;
        padding: 10px 15px 10px 15px;
        color: black;
        width: 250px;
      }

      #firstpage h2 {
        font-size: 1.4rem;
      }

      #mytopics li {
        background-color: #5390d9;
        min-width: 50px;
        min-height: 25px;
        font-size: 1rem;
        padding: 10px 15px 10px 15px;
        border: 1px solid black;
        margin-top: 15px;
        color: white;
      }
    `;
  }

  constructor() {
    super();
    this.loginDisplay = false;
    this.registerDisplay1 = false;
    this.registerDisplay2 = false;
    this.mainPage = false;
    this.firstPage = true;
    this.registerForm = null;
    this.prefferences = null;
    this.lat = '';
    this.lon = '';
    this.errorMessage = '';
    this.successMessage = '';
    this.loggedIn = false;
    this.myUser = null;
    this.nearbyUsers = [];
    this.sameInterestsUsers = [];
  }

  async updated() {
    const storage = read();
    if (storage.length === 0) {
      console.log('No token!');
    } else {
      console.log('Verific daca tokenul e valid');
      const response = await this.checkToken();
      if (response.status === 200) {
        const resp_obj = await response.json();
        console.log('Am primit raspuns ');
        console.log(resp_obj);
        this.myUser = resp_obj;

        //CERERE PENTRU CLOSE USERS
        console.log('FAC REQUEST PT NEARBY USERS');
        const response2 = await this.getNearby();
        if (response2.status == 200) {
          const resp_obj2 = await response2.json();
          this.nearbyUsers = resp_obj2;
          console.log(resp_obj2);
        } else {
          this.nearbyUsers = [];
        }

        //CERERE PENTRU SIMILAR USERS
        console.log('FAC REQUEST PT SAME INTERESTS USERS');
        const response3 = await this.getSameInterests();
        if (response3.status == 200) {
          const resp_obj3 = await response3.json();
          this.sameInterestsUsers = resp_obj3;
          console.log(resp_obj3);
        } else {
          this.sameInterestsUsers = [];
        }

        //CERERE PENTRU SIMILAR USERS
        if (this.myUser.isArtist) {
          console.log('FAC REQUEST PT MY TOPICS');
          const response4 = await this.getMyTopics();
          if (response3.status == 200) {
            const resp_obj4 = await response4.json();
            this.myTopics = resp_obj4;
            console.log(resp_obj4);
          } else {
            this.myTopics = [];
          }
        }

        this._goToMainPage();
        this.loggedIn = true;
        return;
      } else {
        console.log('Token invalid');
        return;
      }
    }
  }

  render() {
    const content = this.getContent();
    return content;
  }

  getContent() {
    console.log(this.firstPage, this.loginDisplay, this.registerDisplay1);
    if (this.firstPage) {
      return html`
        <div id="firstpage">
          ${this.errorMessage.length > 0
            ? html`<h1 class="error">${this.errorMessage}</h1> `
            : html``}
          ${this.successMessage.length > 0
            ? html`<h1 class="success">${this.successMessage}</h1> `
            : html``}
          <h1>SocialME</h1>
          <button id="signupbutton" @click="${this._goToSignUp}">SIGN UP</button>
          <button id="loginbutton" @click="${this._goToLogin}">LOGIN</button>
        </div>
      `;
    }
    if (this.loginDisplay) {
      return html`
        <div id="firstpage">
          ${this.errorMessage.length > 0
            ? html`<h1 class="error">${this.errorMessage}</h1> `
            : html``}
          ${this.successMessage.length > 0
            ? html`<h1 class="success">${this.successMessage}</h1> `
            : html``}
          <form id="loginform" @submit="${this._loginFormSubmitted}">
            <p>
              <label>EMAIL</label><br />
              <input
                id="accemail"
                type="email"
                name="email"
                placeholder="Enter your user email"
                required
              />
            </p>
            <p>
              <label>PASSWORD</label> <br />
              <input
                id="accpassword"
                type="password"
                name="password"
                pattern=".{8,}"
                required
                title="Password must have minimum 8 characters"
                placeholder="Enter your password"
              />
            </p>

            <input class="formbutton" type="submit" value="Login" />
          </form>
        </div>
      `;
    }
    if (this.registerDisplay1) {
      return html`
        <div id="firstpage">
          ${this.errorMessage.length > 0
            ? html`<h1 class="error">${this.errorMessage}</h1> `
            : html``}
          ${this.successMessage.length > 0
            ? html`<h1 class="success">${this.successMessage}</h1> `
            : html``}
          <form
            style="position:relative;padding-bottom: 50px;"
            id="registerForm1"
            @submit="${this._registerForm1Submitted}"
          >
            <h1>Let's get to know eachother</h1>
            <p>How much interesting do you find the following categories? (rate 0 to 5)</p>
            <div class="topic">
              <label> Music</label>
              <img src="./src/music.jpg" />
              <input class="reg1" type="range" step="1" min="0" max="5" />
            </div>

            <div class="topic">
              <label> Plastic arts</label>
              <img src="./src/painting.jpg" />
              <input class="reg1" type="range" step="1" min="0" max="5" />
            </div>

            <div class="topic">
              <label> Theater</label>
              <img src="./src/theatre.jpg" />
              <input class="reg1" type="range" step="1" min="0" max="5" />
            </div>

            <div class="topic">
              <label> Dance</label>
              <img src="./src/dancers.jpg" />
              <input class="reg1" type="range" step="1" min="0" max="5" />
            </div>
            <input
              type="submit"
              value=""
              style="width:50px;height:50px; 
              background-image:url('./src/arrow.png');
              background-size:cover;
              border:1px solid black;
              border-radius:25px;
              position:absolute;
              bottom:20px;
              right:20px;"
            />
          </form>

          <form id="registerForm2" @submit="${this._registerForm2Submitted}">
            <h1>Please fill in the following information about you</h1>
            <h2>Who are you? (click your option)</h2>
            <label for="radio1">
              <div id="option1" class="optionable">
                <label>I am an artist</label>
                <input id="radio1" value="0" type="radio" name="artist" class="reg3" />
                <img src="./src/artist.jpg" class="img3" />
              </div>
            </label>
            <label for="radio2">
              <div id="option2" class="optionable">
                <label>I just love art</label>
                <input
                  id="radio2"
                  value="1"
                  type="radio"
                  name="artist"
                  class="reg3"
                  checked="true"
                />
                <img src="./src/artlover.png" class="img3" />
              </div>
            </label>
            <br />
            <div class="formEntry">
              <label>Nickname</label>
              <input placeholder="Enter your nickname" name="nickname" class="reg2" type="text" />
            </div>

            <div class="formEntry">
              <label>Firstname</label>
              <input
                placeholder="Enter your first name"
                name="firstName"
                class="reg2"
                type="text"
              />
            </div>

            <div class="formEntry">
              <label>Lastname</label>
              <input placeholder="Enter your last name" name="lastName" class="reg2" type="text" />
            </div>

            <div class="formEntry">
              <label>Phone number</label>
              <input
                placeholder="Enter your phone number"
                name="phoneNumber"
                class="reg2"
                type="tel"
              />
            </div>

            <div class="formEntry">
              <label>Email</label>
              <input
                placeholder="Enter your email address"
                name="email"
                class="reg2"
                type="email"
              />
            </div>

            <div class="formEntry">
              <label>Password</label>
              <input
                placeholder="Enter your password"
                name="password"
                class="reg2"
                type="password"
              />
            </div>
            <input
              type="submit"
              value=""
              style="width:50px;height:50px; 
              background-image:url('./src/arrow.png');
              background-size:cover;
              border:1px solid black;
              border-radius:25px;
              position:absolute;
              bottom:20px;
              right:20px;"
            />
          </form>
        </div>
      `;
    }

    if (this.mainPage) {
      return html`
        <div id="firstpage">
          ${this.errorMessage.length > 0
            ? html`<h1 class="error">${this.errorMessage}</h1> `
            : html``}
          ${this.successMessage.length > 0
            ? html`<h1 class="success">${this.successMessage}</h1> `
            : html``}
          <div id="profile">
            <label
              ><b>${this.myUser ? html`${this.myUser.nickname}` : html`Nobody`}'s profile</b></label
            >
            <p>
              ${this.myUser && this.myUser.isArtist
                ? html`Congrats you are an artist`
                : html`Congrats you are an art lover`}
            </p>
          </div>

          <br />
          <div style="margin-bottom: 30px;color:black;">
            ${this.myUser && this.myUser.isArtist
              ? html`<span style="font-family:Courier; color:#7400B8;">Create new topic </span
                  ><br />
                  <form style="margin-top:15px;" id="topicForm" @submit="${this._createTopic}">
                    <input name="topicTitle" type="text" />
                    <input
                      style="color:purple;font-family:Courier;padding:5px 5px 5px 5px; "
                      type="submit"
                      value="Create"
                    />
                  </form> `
              : html``}
            <br />
          </div>

          ${this.myUser && this.myUser.isArtist
            ? html`
                <div id="mytopics">
                  ${this.myTopics != null
                    ? html` <h2>My topics</h2>
                        <hr />
                        <ul style="list-style-type:none;">
                          ${this.myTopics.map(
                            item =>
                              html`<li>
                                ${item.topicTitle}<br />
                                By ${item.ownerNickname}
                              </li>`
                          )}
                        </ul>
                        <hr />`
                    : html`You have no topics created by you`}
                </div>
              `
            : html``}

          <div id="nearby">
            ${this.nearbyUsers.length == 0
              ? html`No user to your location`
              : html` <h2 style="color:#7400B8">Users around you</h2>
                  <div class="user">
                    ${this.nearbyUsers[0].nickname}<br />
                    ${this.nearbyUsers[0].isArtist ? html` ARTIST` : html` NOT ARTIST`}
                  </div>
                  <div class="user">
                    ${this.nearbyUsers[1].nickname}<br />
                    ${this.nearbyUsers[1].isArtist ? html` ARTIST` : html` NOT ARTIST`}
                  </div>
                  <div class="user">
                    ${this.nearbyUsers[2].nickname}<br />
                    ${this.nearbyUsers[2].isArtist ? html` ARTIST` : html` NOT ARTIST`}
                  </div>`}
          </div>
          <div id="similar">
            ${this.sameInterestsUsers.length == 0
              ? html`No user with similar interests as you`
              : html` <h2 style="color:#7400B8">Users with similar interests as you</h2>
                  <div class="user">
                    ${this.sameInterestsUsers[0].nickname}<br />
                    ${this.sameInterestsUsers[0].isArtist ? html` ARTIST` : html` NOT ARTIST`}
                  </div>
                  <div class="user">
                    ${this.sameInterestsUsers[1].nickname}<br />
                    ${this.sameInterestsUsers[1].isArtist ? html` ARTIST` : html` NOT ARTIST`}
                  </div>
                  <div class="user">
                    ${this.sameInterestsUsers[2].nickname}<br />
                    ${this.sameInterestsUsers[2].isArtist ? html` ARTIST` : html` NOT ARTIST`}
                  </div>`}
          </div>
        </div>
      `;
    }
  }

  selectOption1(event) {
    console.log(document.getElementById('option1'));
  }

  selectOption2(event) {}

  _registerForm1Submitted(event) {
    event.preventDefault();
    const form1 = event.target;
    const pref = form1.getElementsByClassName('reg1');
    let prefferences = [];
    console.log(pref);
    let i;
    for (i = 0; i < pref.length; i++) {
      prefferences.push(parseInt(pref[i].value));
    }
    this.prefferences = prefferences;
    event.target.parentElement.children[0].style.display = 'none';
    event.target.parentElement.children[1].style.display = 'block';

    return;
  }

  async _registerForm2Submitted(event) {
    event.preventDefault();
    this.registerForm = new Object();
    console.log('HEI', event.target);

    const pref = event.target.getElementsByClassName('reg2');
    console.log(pref);
    let i;
    for (i = 0; i < pref.length; i++) {
      this.registerForm[pref[i].name] = pref[i].value;
    }
    this.registerForm['interests'] = this.prefferences;
    this.registerForm['latitude'] = '0.00';
    this.registerForm['longitude'] = '0.00';

    const artistButtons = event.target.getElementsByClassName('reg3');

    const isArtist = artistButtons[0].checked ? true : false;
    this.registerForm['isArtist'] = isArtist;

    console.log(this.registerForm);

    const response = await this.registerUser(this.registerForm);
    if (response.status === 201) {
      const resp_obj = await response.json();
      console.log(resp_obj);
      this.displaySuccess('Inregistrare reușită');
      this._goToLogin();
      return;
    } else {
      this.displayError('Inregistrare esuata');
      this._goToFirstPage();
      return;
    }
  }

  _onGeoSuccess(position) {
    this.lat = position.coords.latitude;
    this.lon = position.coords.longitude;
  }

  _onGeoError() {
    alert('Unable to retrieve the current location');
  }

  async _createTopic(event) {
    event.preventDefault();
    const form = event.target;
    const data = new FormData(form);
    const object = Object.fromEntries(data);
    object['token'] = read()[0]['token'];
    console.log('TRIMIT');
    console.log(object);
    const response = await this.submitTopic(object);
    if (response.status === 201) {
      const resp_obj = await response.json();
      console.log(resp_obj);
      return;
    } else {
      return;
    }
  }

  async submitTopic(object) {
    const response = await fetch('http://localhost:8081/socialme/topics', {
      method: 'POST',
      mode: 'cors',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(object), // body data type must match "Content-Type" header
    });
    return response;
  }

  async _loginFormSubmitted(event) {
    event.preventDefault();
    const form = event.target;
    const data = new FormData(form);
    const object = Object.fromEntries(data);
    // completeaza cu latitudine si longitudine

    var getPosition = function (options) {
      return new Promise(function (resolve, reject) {
        navigator.geolocation.getCurrentPosition(resolve, reject, options);
      });
    };

    await getPosition()
      .then(position => {
        this.lat = position.coords.latitude;
        this.lon = position.coords.longitude;
        object['latitude'] = this.lat;
        object['longitude'] = this.lon;
      })
      .catch(err => {
        console.error(err.message);
      });

    console.log('=========');
    console.log(object);
    const response = await this.loginUser(object);
    console.log('AM FACUT LOGIN');
    if (response.status === 200) {
      const resp_obj = await response.json();
      console.log(resp_obj);

      sessionEstablish(resp_obj);
      this._goToMainPage();
      return;
    } else {
      this.displayError('Login esuat');
      this._goToFirstPage();
      return;
    }
  }

  async getNearby() {
    const response = await fetch('http://localhost:8081/socialme/close/' + read()[0]['token'], {
      method: 'GET',
      mode: 'cors',
      headers: {
        'Content-Type': 'application/json',
      },
    });
    return response;
  }

  async getSameInterests() {
    const response = await fetch('http://localhost:8081/socialme/similar/' + read()[0]['token'], {
      method: 'GET',
      mode: 'cors',
      headers: {
        'Content-Type': 'application/json',
      },
    });
    return response;
  }

  async getMyTopics() {
    const response = await fetch(
      'http://localhost:8081/socialme/topics/all/' + read()[0]['token'],
      {
        method: 'GET',
        mode: 'cors',
        headers: {
          'Content-Type': 'application/json',
        },
      }
    );
    return response;
  }

  displayError(msg) {
    this.errorMessage = msg;
    this.update();
  }

  displaySuccess(msg) {
    this.successMessage = msg;
    this.update();
  }

  _goToFirstPage() {
    this.loginDisplay = false;
    this.registerDisplay1 = false;
    this.registerDisplay2 = false;
    this.firstPage = true;
    this.mainPage = false;
    this.errorMessage = '';
    this.update();
  }

  _goToLogin(event) {
    console.log('login');
    this.loginDisplay = true;
    this.registerDisplay1 = false;
    this.registerDisplay2 = false;
    this.firstPage = false;
    this.mainPage = false;
    this.errorMessage = '';
    this.update();
  }

  _goToMainPage(event) {
    console.log('login');
    this.loginDisplay = false;
    this.registerDisplay1 = false;
    this.registerDisplay2 = false;
    this.firstPage = false;
    this.mainPage = true;
    this.errorMessage = '';
    this.successMessage = '';
    this.update();
  }

  _goToSignUp(event) {
    console.log('register');
    this.loginDisplay = false;
    this.registerDisplay1 = true;
    this.registerDisplay2 = false;
    this.firstPage = false;
    this.mainPage = false;
    this.errorMessage = '';
    this.update();
  }

  _nextRegister(event) {
    console.log('AOLEU GUCCI');
    this.loginDisplay = false;
    this.registerDisplay1 = false;
    this.registerDisplay2 = true;
    this.firstPage = false;
    this.mainPage = false;
    this.update();
  }

  async loginUser(object) {
    const response = await fetch('http://localhost:8081/socialme/login', {
      method: 'POST',
      mode: 'cors',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(object), // body data type must match "Content-Type" header
    });
    return response;
  }

  async registerUser(object) {
    const response = await fetch('http://localhost:8081/socialme/register', {
      method: 'POST',
      mode: 'cors',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(object), // body data type must match "Content-Type" header
    });
    return response;
  }

  async registerUser(object) {
    const response = await fetch('http://localhost:8081/socialme/register', {
      method: 'POST',
      mode: 'cors',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(object), // body data type must match "Content-Type" header
    });
    return response;
  }

  async checkToken() {
    const response = await fetch(
      'http://localhost:8081/socialme/checkToken/' + read()[0]['token'],
      {
        method: 'GET',
        mode: 'cors',
        headers: {
          'Content-Type': 'application/json',
        },
      }
    );
    return response;
  }

  _logoutFormSubmitted(event) {
    event.preventDefault();
    removeSession();
    this.logindisplay = true;
    // dispatch event to parent to say that logout has been realised
    this.dispatchEvent(new CustomEvent('form-submitted', { detail: 0 }));
  }
}
window.customElements.define('ing-header', ingHeader);
