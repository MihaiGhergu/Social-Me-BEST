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
        display: inline-block;
        margin: 25px 25px 0px 25px;
        width: 200px;
      }

      #registerForm2 {
        display: none;
      }
    `;
  }

  constructor() {
    super();
    this.loginDisplay = false;
    this.registerDisplay1 = false;
    this.registerDisplay2 = false;
    this.firstPage = true;
    this.registerForm = null;
    this.prefferences = null;
    this.lat = '';
    this.lon = '';
  }

  updated() {
    const storage = read();
    if (storage.length === 0) {
      this.loginDisplay = true;
      this.dispatchEvent(new CustomEvent('session-init', { detail: 0 }));
    } else {
      this.loginDisplay = false;
      this.dispatchEvent(new CustomEvent('session-init', { detail: 1 }));
    }
  }

  render() {
    console.log('RENDER');
    const content = this.getContent();
    return content;
  }

  getContent() {
    console.log(this.firstPage, this.loginDisplay, this.registerDisplay1);
    if (this.firstPage) {
      return html`
        <div id="firstpage">
          <h1>SocialME</h1>
          <button id="signupbutton" @click="${this._goToSignUp}">SIGN UP</button>
          <button id="loginbutton" @click="${this._goToLogin}">LOGIN</button>
        </div>
      `;
    }
    if (this.loginDisplay) {
      return html`
        <div id="firstpage">
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
          <form
            style="position:relative;padding-bottom: 50px;"
            id="registerForm1"
            @submit="${this._registerForm1Submitted}"
          >
            <h1>Hai să ne cunoaștem</h1>
            <p>Cat de interesante sunt urmatoarele domenii pentru tine?</p>
            <div class="topic">
              <label> Muzică</label>
              <img src="./src/music.jpg" />
              <input class="reg1" type="range" step="1" min="0" max="5" />
            </div>

            <div class="topic">
              <label> Arte plastice</label>
              <img src="./src/painting.jpg" />
              <input class="reg1" type="range" step="1" min="0" max="5" />
            </div>

            <div class="topic">
              <label> Teatru</label>
              <img src="./src/theatre.jpg" />
              <input class="reg1" type="range" step="1" min="0" max="5" />
            </div>

            <div class="topic">
              <label> Dans</label>
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
            <h1>Completează câteva informații despre tine</h1>

            <p>
              <br />
              Ești un artist sau un iubitor de artă? <br />
              Artist <input value="0" type="radio" name="artist" class="reg3" /> <br />
              Iubitor de artă
              <input value="1" type="radio" name="artist" class="reg3" checked="true" />
            </p>
            <p>
              <label>User name(nickname)</label>
              <input name="nickname" class="reg2" type="text" />
            </p>

            <p>
              <label>Firstname</label>
              <input name="firstName" class="reg2" type="text" />
            </p>

            <p>
              <label>Lastname</label>
              <input name="lastName" class="reg2" type="text" />
            </p>

            <p>
              <label>Phone number</label>
              <input name="phoneNumber" class="reg2" type="tel" />
            </p>

            <p>
              <label>Email</label>
              <input name="email" class="reg2" type="email" />
            </p>

            <p>
              <label>Password</label>
              <input name="password" class="reg2" type="password" />
            </p>
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

    if (this.mainMenu && this.logged) {
      return html` YOU ARE LOGGED IN `;
    }
  }

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
      return;
      sessionEstablish(resp_obj);
      this.logindisplay = false;
      // dispatch event to parent to say that login has been realised
      this.dispatchEvent(new CustomEvent('form-submitted', { detail: 1 }));
    } else {
      console.log('REGISTER ESUAT');
      return;
      this.logindisplay = true;
      // dispatch event to parent to say that login has failed
      this.dispatchEvent(new CustomEvent('form-submitted', { detail: -1 }));
    }
  }

  _onGeoSuccess(position) {
    this.lat = position.coords.latitude;
    this.lon = position.coords.longitude;
  }

  _onGeoError() {
    alert('Unable to retrieve the current location');
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
      // GET NEARBY
      const response2 = await this.getNearby();
      if (response2.status === 200) {
        const resp_obj2 = await response2.json();
        console.log(resp_obj2);
      } else console.log('BOU');
      return;
    } else {
      console.log('LOGIN ESUAT');
      return;
    }
  }

  async getNearby() {
    console.log('FAC REQUEST LA http://localhost:8081/socialme/close/' + read()[0]['token']);
    const response = await fetch('http://localhost:8081/socialme/close/' + read()[0]['token'], {
      method: 'GET',
      mode: 'cors',
      headers: {
        'Content-Type': 'application/json',
      },
    });
    return response;
  }

  _goToLogin(event) {
    console.log('login');
    this.loginDisplay = true;
    this.registerDisplay1 = false;
    this.registerDisplay2 = false;
    this.firstPage = false;
    this.update();
  }

  _goToSignUp(event) {
    console.log('register');
    this.loginDisplay = false;
    this.registerDisplay1 = true;
    this.registerDisplay2 = false;
    this.firstPage = false;
    this.update();
  }

  _nextRegister(event) {
    console.log('AOLEU GUCCI');
    this.loginDisplay = false;
    this.registerDisplay1 = false;
    this.registerDisplay2 = true;
    this.firstPage = false;
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

  _logoutFormSubmitted(event) {
    event.preventDefault();
    removeSession();
    this.logindisplay = true;
    // dispatch event to parent to say that logout has been realised
    this.dispatchEvent(new CustomEvent('form-submitted', { detail: 0 }));
  }
}
window.customElements.define('ing-header', ingHeader);
