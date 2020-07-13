import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IExpeditor } from 'app/shared/model/expeditor.model';

@Component({
  selector: 'jhi-expeditor-detail',
  templateUrl: './expeditor-detail.component.html'
})
export class ExpeditorDetailComponent implements OnInit {
  expeditor: IExpeditor;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ expeditor }) => {
      this.expeditor = expeditor;
    });
  }

  previousState() {
    window.history.back();
  }
}
