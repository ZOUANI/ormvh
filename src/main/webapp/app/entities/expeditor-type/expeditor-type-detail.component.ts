import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IExpeditorType } from 'app/shared/model/expeditor-type.model';

@Component({
  selector: 'jhi-expeditor-type-detail',
  templateUrl: './expeditor-type-detail.component.html',
})
export class ExpeditorTypeDetailComponent implements OnInit {
  expeditorType: IExpeditorType | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ expeditorType }) => (this.expeditorType = expeditorType));
  }

  previousState(): void {
    window.history.back();
  }
}
