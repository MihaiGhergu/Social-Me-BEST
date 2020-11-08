import { LitElement, html, css } from 'lit-element';

import { sessionEstablish, read, removeSession } from './storage.js';

import './ingHeader.js';

export class ingMain extends LitElement {
  static get properties() {
    return {
      response: { type: Object },
      content_value: { type: String },
      logindisplay: { type: Boolean },
      account: { type: Object },
      accountString: { type: String },
      transactions: { type: Object },
      transactionsString: { type: String },
      message: { type: String },
      pos: { type: Boolean },
    };
  }

  static get styles() {
    return css`
      #loginform input {
        padding: 0.5em 1em 0.5em 1em;
      }

      ing-header {
        width: 100%;
        display: flex;
        flex-direction: row;
        text-align: center;
        border: 3px solid #33415c;
        background: #0466c8;
      }
    `;
  }

  constructor() {
    super();
    this.response = [];
    this.content_value = '';
    this.account = null;
    this.accountString = JSON.stringify(this.account);
    this.transactions = null;
    this.transactionsString = JSON.stringify(this.translate);
    this.message = '';
  }

  render() {
    return html`
      <ing-header
        message="${this.message}"
        @session-init="${this._onSessionInit}"
        @form-submitted="${this._onFormSignal}"
      >
      </ing-header>
    `;
  }
}
