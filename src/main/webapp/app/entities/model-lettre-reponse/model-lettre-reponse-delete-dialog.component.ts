import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IModelLettreReponse } from 'app/shared/model/model-lettre-reponse.model';
import { ModelLettreReponseService } from './model-lettre-reponse.service';

@Component({
  selector: 'jhi-model-lettre-reponse-delete-dialog',
  templateUrl: './model-lettre-reponse-delete-dialog.component.html'
})
export class ModelLettreReponseDeleteDialogComponent {
  modelLettreReponse: IModelLettreReponse;

  constructor(
    protected modelLettreReponseService: ModelLettreReponseService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.modelLettreReponseService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'modelLettreReponseListModification',
        content: 'Deleted an modelLettreReponse'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-model-lettre-reponse-delete-popup',
  template: ''
})
export class ModelLettreReponseDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ modelLettreReponse }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ModelLettreReponseDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.modelLettreReponse = modelLettreReponse;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/model-lettre-reponse', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/model-lettre-reponse', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
