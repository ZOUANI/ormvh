import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { ICourrier } from 'app/shared/model/courrier.model';

@Component({
  selector: 'jhi-courrier-detail',
  templateUrl: './courrier-detail.component.html',
})
export class CourrierDetailComponent implements OnInit {
  courrier: ICourrier | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ courrier }) => (this.courrier = courrier));
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType = '', base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  previousState(): void {
    window.history.back();
  }
}
