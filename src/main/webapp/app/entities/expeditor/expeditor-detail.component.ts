import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IExpeditor } from 'app/shared/model/expeditor.model';

@Component({
  selector: 'jhi-expeditor-detail',
  templateUrl: './expeditor-detail.component.html',
})
export class ExpeditorDetailComponent implements OnInit {
  expeditor: IExpeditor | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ expeditor }) => (this.expeditor = expeditor));
  }

  previousState(): void {
    window.history.back();
  }
}
