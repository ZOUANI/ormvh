import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICategorieModelLettreReponse } from 'app/shared/model/categorie-model-lettre-reponse.model';
import { CategorieModelLettreReponseService } from './categorie-model-lettre-reponse.service';

@Component({
  selector: 'jhi-categorie-model-lettre-reponse-delete-dialog',
  templateUrl: './categorie-model-lettre-reponse-delete-dialog.component.html'
})
export class CategorieModelLettreReponseDeleteDialogComponent {
  categorieModelLettreReponse: ICategorieModelLettreReponse;

  constructor(
    protected categorieModelLettreReponseService: CategorieModelLettreReponseService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.categorieModelLettreReponseService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'categorieModelLettreReponseListModification',
        content: 'Deleted an categorieModelLettreReponse'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-categorie-model-lettre-reponse-delete-popup',
  template: ''
})
export class CategorieModelLettreReponseDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ categorieModelLettreReponse }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(CategorieModelLettreReponseDeleteDialogComponent as Component, {
          size: 'lg',
          backdrop: 'static'
        });
        this.ngbModalRef.componentInstance.categorieModelLettreReponse = categorieModelLettreReponse;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/categorie-model-lettre-reponse', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/categorie-model-lettre-reponse', { outlets: { popup: null } }]);
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
