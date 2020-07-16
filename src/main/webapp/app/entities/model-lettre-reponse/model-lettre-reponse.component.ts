import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IModelLettreReponse } from 'app/shared/model/model-lettre-reponse.model';
import { ModelLettreReponseService } from './model-lettre-reponse.service';
import { ModelLettreReponseDeleteDialogComponent } from './model-lettre-reponse-delete-dialog.component';

@Component({
  selector: 'jhi-model-lettre-reponse',
  templateUrl: './model-lettre-reponse.component.html',
})
export class ModelLettreReponseComponent implements OnInit, OnDestroy {
  modelLettreReponses?: IModelLettreReponse[];
  eventSubscriber?: Subscription;

  constructor(
    protected modelLettreReponseService: ModelLettreReponseService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.modelLettreReponseService
      .query()
      .subscribe((res: HttpResponse<IModelLettreReponse[]>) => (this.modelLettreReponses = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInModelLettreReponses();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IModelLettreReponse): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInModelLettreReponses(): void {
    this.eventSubscriber = this.eventManager.subscribe('modelLettreReponseListModification', () => this.loadAll());
  }

  delete(modelLettreReponse: IModelLettreReponse): void {
    const modalRef = this.modalService.open(ModelLettreReponseDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.modelLettreReponse = modelLettreReponse;
  }
}
