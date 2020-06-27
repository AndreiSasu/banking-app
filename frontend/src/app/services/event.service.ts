import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class EventService<T> {

  subject = new Subject<T>();

  constructor() {}

  getSubject(): Subject<T> {
    return this.subject;
  }
}
