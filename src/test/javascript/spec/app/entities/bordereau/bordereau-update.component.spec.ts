import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { OrmvahTestModule } from '../../../test.module';
import { BordereauUpdateComponent } from 'app/entities/bordereau/bordereau-update.component';
import { BordereauService } from 'app/entities/bordereau/bordereau.service';
import { Bordereau } from 'app/shared/model/bordereau.model';

describe('Component Tests', () => {
  describe('Bordereau Management Update Component', () => {
    let comp: BordereauUpdateComponent;
    let fixture: ComponentFixture<BordereauUpdateComponent>;
    let service: BordereauService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [OrmvahTestModule],
        declarations: [BordereauUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(BordereauUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BordereauUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BordereauService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Bordereau(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Bordereau();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
