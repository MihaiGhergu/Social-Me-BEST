import { LitElement, html, css } from 'lit-element';

import { sessionEstablish, read, removeSession } from './storage.js';

import './ingContent.js';
import './ingMenu.js';
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

      ing-content {
        background: #33415c;
        display: flex;
        flex-direction: column;
        width: 100%;
        border: 3px solid #33415c;
        padding: 0.6rem;
        color: white;
      }

      ing-menu {
        width: 100%;
        display: flex;
        border: 3px solid #33415c;
        flex-direction: column;
        padding: 0.6rem;
        background: #33415c;
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

      <!-- <ing-content
        @message-sent="${this._onMessageRecieved}"
        content_value=${this.content_value}
        accountString=${this.accountString}
        transactionsString=${this.transactionsString}
      >
      </ing-content>

      <ing-menu @option-selected="${this._onOptionSelected}" content_value=${this.content_value}>
      </ing-menu> -->
    `;
  }

  _onMessageRecieved(event) {
    if (event.detail == 0) {
      this.message = 'Transaction failed';
    } else {
      this.message = 'Transaction succesfully';
    }
  }

  _onOptionSelected(event) {
    switch (event.detail) {
      case 1:
        this.content_value = 2;
        break;
      case 2:
        this.getInfoFromDatabase();
        this.content_value = 3;
        break;
      case 3:
        this.getTransactionsFromDatabase();
        this.content_value = 4;
        break;
    }
  }

  async getTransactionsFromDatabase() {
    const data = await this.fetchTransactions();
    this.transactions = data;
    this.transactionsString = JSON.stringify(this.transactions);
  }

  async fetchTransactions() {
    const acc_id = read()[0].id;
    const response = await fetch('http://localhost:8081/transaction/all/' + acc_id);
    if (response.ok) {
      const data = await response.json();
      return data;
    }
    return null;
  }

  async getInfoFromDatabase() {
    const data = await this.fetchUser();
    this.account = data;
    this.accountString = JSON.stringify(this.account);
  }

  async fetchUser() {
    const acc_id = read()[0].id;
    const response = await fetch('http://localhost:8081/bankAccount/' + acc_id);
    if (response.ok) {
      const data = await response.json();
      return data;
    }
    return null;
  }

  _onFormSignal(event) {
    this.content_value = event.detail;
  }

  _onSessionInit(event) {
    this.content_value = event.detail;
  }
}
